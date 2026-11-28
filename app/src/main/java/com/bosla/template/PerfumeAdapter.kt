package com.bosla.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosla.template.databinding.ItemPerfumeBinding

class PerfumeAdapter(
    private var items: List<Perfume>,
    private val onItemClick: (Perfume) -> Unit,
    private val onFavoriteClick: (Perfume) -> Unit,
    private val onCartClick: (Perfume) -> Unit
) : RecyclerView.Adapter<PerfumeAdapter.PerfumeViewHolder>() {

    inner class PerfumeViewHolder(val binding: ItemPerfumeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfumeViewHolder {
        val binding = ItemPerfumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PerfumeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PerfumeViewHolder, position: Int) {
        val perfume = items[position]
        with(holder.binding) {
            tvName.text = perfume.name
            tvCategory.text = perfume.category
            tvRating.text = root.context.getString(R.string.rating_prefix) + perfume.rating.toString()
            tvPrice.text = "${perfume.price.toInt()} ${root.context.getString(R.string.currency)}"

            val favIcon = if (perfume.isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_border
            btnFavorite.setImageResource(favIcon)

            btnAddCart.text = if (perfume.isInCart) {
                root.context.getString(R.string.in_cart)
            } else {
                root.context.getString(R.string.add_to_cart)
            }

            root.setOnClickListener { onItemClick(perfume) }
            btnFavorite.setOnClickListener { onFavoriteClick(perfume) }
            btnAddCart.setOnClickListener { onCartClick(perfume) }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Perfume>) {
        items = newItems
        notifyDataSetChanged()
    }
}
