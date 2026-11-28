package com.bosla.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosla.template.databinding.ItemCartBinding

class CartAdapter(
    private var items: List<Perfume>,
    private val onQuantityChanged: (Perfume, Int) -> Unit,
    private val onRemoveItem: (Perfume) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val perfume = items[position]
        with(holder.binding) {
            txtCartName.text = perfume.name
            txtCartUnitPrice.text = "${perfume.price} ${root.context.getString(R.string.currency)}"
            txtQty.text = perfume.cartQuantity.toString()

            btnPlus.setOnClickListener {
                onQuantityChanged(perfume, perfume.cartQuantity + 1)
            }

            btnMinus.setOnClickListener {
                if (perfume.cartQuantity > 1) {
                    onQuantityChanged(perfume, perfume.cartQuantity - 1)
                } else {
                    onRemoveItem(perfume)
                }
            }

            btnRemove.setOnClickListener {
                onRemoveItem(perfume)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Perfume>) {
        items = newItems
        notifyDataSetChanged()
    }
}
