package work.jsfr.uuidgenerator.ui.uuid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import work.jsfr.uuidgenerator.databinding.ItemUuidBatchBinding

class UuidListAdapter(
    private val onCopyClick: (String) -> Unit
) : ListAdapter<String, UuidListAdapter.UuidViewHolder>(UuidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UuidViewHolder {
        val binding = ItemUuidBatchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UuidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UuidViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UuidViewHolder(
        private val binding: ItemUuidBatchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uuid: String) {
            binding.textUuidItem.text = uuid
            binding.buttonCopyItem.setOnClickListener {
                onCopyClick(uuid)
            }
        }
    }

    private class UuidDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
