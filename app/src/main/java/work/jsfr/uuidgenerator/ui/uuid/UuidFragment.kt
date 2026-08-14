package work.jsfr.uuidgenerator.ui.uuid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import work.jsfr.uuidgenerator.R
import work.jsfr.uuidgenerator.databinding.FragmentUuidBinding
import work.jsfr.uuidgenerator.utils.UiUtils

class UuidFragment : Fragment() {
    private var _binding: FragmentUuidBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uuidViewModel =
            ViewModelProvider(this).get(UuidViewModel::class.java)

        _binding = FragmentUuidBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Bind Single UUID UI elements
        uuidViewModel.currentUuid.observe(viewLifecycleOwner) { uuid ->
            binding.textUuid.text = uuid
        }

        binding.buttonGenerate.setOnClickListener {
            uuidViewModel.generateSingle()
        }

        binding.buttonCopy.setOnClickListener {
            uuidViewModel.currentUuid.value?.let { uuid ->
                exportToClipboard(uuid)
            }
        }

        // Initialize view states from ViewModel
        binding.chipGroupVersion.check(
            if (uuidViewModel.getVersion().equals("v7", ignoreCase = true)) {
                R.id.chip_v7
            } else {
                R.id.chip_v4
            }
        )
        binding.switchUppercase.isChecked = uuidViewModel.isUppercase()
        binding.switchRemoveHyphens.isChecked = uuidViewModel.isRemoveHyphens()
        binding.sliderBatchCount.value = uuidViewModel.getBatchCount().toFloat()
        binding.textBatchCount.text = uuidViewModel.getBatchCount().toString()

        // Bind Config Panel listeners
        binding.chipGroupVersion.setOnCheckedStateChangeListener { _, checkedIds ->
            val version = if (checkedIds.contains(R.id.chip_v7)) "v7" else "v4"
            uuidViewModel.setVersion(version)
        }

        binding.switchUppercase.setOnCheckedChangeListener { _, isChecked ->
            uuidViewModel.setUppercase(isChecked)
        }

        binding.switchRemoveHyphens.setOnCheckedChangeListener { _, isChecked ->
            uuidViewModel.setRemoveHyphens(isChecked)
        }

        binding.sliderBatchCount.addOnChangeListener { _, value, _ ->
            val count = value.toInt()
            binding.textBatchCount.text = count.toString()
            uuidViewModel.setBatchCount(count)
        }

        // Setup RecyclerView for batch generation
        val listAdapter = UuidListAdapter { uuid ->
            exportToClipboard(uuid)
        }
        binding.recyclerViewBatch.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe batch generation list
        uuidViewModel.batchUuids.observe(viewLifecycleOwner) { uuids ->
            if (uuids.isNullOrEmpty()) {
                binding.layoutEmptyState.visibility = View.VISIBLE
                binding.recyclerViewBatch.visibility = View.GONE
            } else {
                binding.layoutEmptyState.visibility = View.GONE
                binding.recyclerViewBatch.visibility = View.VISIBLE
                listAdapter.submitList(uuids)
            }
        }

        // Bind Batch Panel actions
        binding.buttonGenerateBatch.setOnClickListener {
            uuidViewModel.generateBatch()
        }

        binding.buttonCopyAll.setOnClickListener {
            val uuids = uuidViewModel.batchUuids.value
            if (!uuids.isNullOrEmpty()) {
                val joined = uuids.joinToString("\n")
                exportToClipboard(joined)
            } else {
                UiUtils.showToast("没有可复制的 UUID")
            }
        }

        return root
    }

    private fun exportToClipboard(text: String) {
        UiUtils.copyText(text)
        UiUtils.showToast("复制成功", false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}