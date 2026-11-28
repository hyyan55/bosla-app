package com.bosla.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosla.template.databinding.ItemFeaturedNoteBinding

class FeaturedAdapter(
    private var items: List<NoteItem>,
    private val onItemClick: (NoteItem) -> Unit
) : RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder>() {

    fun updateData(newItems: List<NoteItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val binding = ItemFeaturedNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeaturedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.alpha = 0f
        holder.itemView.translationY = 16f
        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(250)
            .start()
    }

    override fun getItemCount(): Int = items.size

    inner class FeaturedViewHolder(private val binding: ItemFeaturedNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItem) {
            binding.tvFeaturedTitle.text = item.title
            binding.tvFeaturedDesc.text = item.description
            binding.tvFeaturedCat.text = item.category

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
