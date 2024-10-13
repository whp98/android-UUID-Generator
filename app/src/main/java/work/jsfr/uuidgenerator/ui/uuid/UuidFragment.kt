package work.jsfr.uuidgenerator.ui.uuid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        val textView: TextView = binding.textUuid
        uuidViewModel.generatedUuid.observe(viewLifecycleOwner) { uuid ->
            textView.text = uuid
        }
        //生成按钮监听
        val buttonGenerate: Button = binding.buttonGenerate
        val buttonCopy: Button = binding.buttonCopy
        buttonGenerate.setOnClickListener {
            uuidViewModel.generateUuid()
        }
        buttonCopy.setOnClickListener {
            exportToClipboard(textView.text.toString())
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