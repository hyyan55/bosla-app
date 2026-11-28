package com.bosla.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosla.template.databinding.ItemCartBinding

class CartAdapter(
    private var items: List<Perfume>,
    private val onRemoveClick: (Perfume) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val perfume = items[position]
        with(holder.binding) {
            tvCartName.text = perfume.name
            tvCartPrice.text = "${perfume.price.toInt()} ${root.context.getString(R.string.currency)}"
            btnRemove.setOnClickListener { onRemoveClick(perfume) }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Perfume>) {
        items = newItems
        notifyDataSetChanged()
    }
}
