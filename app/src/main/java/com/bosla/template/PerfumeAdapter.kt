package com.bosla.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosla.template.databinding.ItemPerfumeBinding

class PerfumeAdapter(
    private var items: List<Perfume>,
    private val onItemClick: (Perfume) -> Unit,
    private val onFavToggle: (Perfume) -> Unit,
    private val onAddToCart: (Perfume) -> Unit
) : RecyclerView.Adapter<PerfumeAdapter.PerfumeViewHolder>() {

    inner class PerfumeViewHolder(val binding: ItemPerfumeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfumeViewHolder {
        val binding = ItemPerfumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PerfumeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PerfumeViewHolder, position: Int) {
        val perfume = items[position]
        with(holder.binding) {
            txtPerfumeName.text = perfume.name
            txtBrand.text = perfume.brand
            txtPrice.text = "${perfume.price} ${root.context.getString(R.string.currency)}"
            txtCategoryTag.text = perfume.category

            btnFav.setImageResource(
                if (perfume.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            )

            btnAddToCart.text = if (perfume.inCart) {
                root.context.getString(R.string.in_cart)
            } else {
                root.context.getString(R.string.add_to_cart)
            }

            btnFav.setOnClickListener {
                onFavToggle(perfume)
            }

            btnAddToCart.setOnClickListener {
                onAddToCart(perfume)
            }

            root.setOnClickListener {
                onItemClick(perfume)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Perfume>) {
        items = newItems
        notifyDataSetChanged()
    }
}
