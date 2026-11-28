package com.bosla.template

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosla.template.databinding.ItemNoteGridBinding

class NotesAdapter(
    private var items: List<NoteItem>,
    private val onItemClick: (NoteItem) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    fun updateData(newItems: List<NoteItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        // دخول عناصر القائمة بحركة خفيفة
        holder.itemView.alpha = 0f
        holder.itemView.translationY = 24f
        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(250)
            .start()
    }

    override fun getItemCount(): Int = items.size

    inner class NoteViewHolder(private val binding: ItemNoteGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItem) {
            binding.tvItemTitle.text = item.title
            binding.tvItemDesc.text = item.description
            binding.tvItemCategory.text = item.category
            binding.tvItemDate.text = item.date

            if (item.isTask && item.isCompleted) {
                binding.ivStatusIcon.visibility = View.VISIBLE
            } else {
                binding.ivStatusIcon.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
