package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosla.template.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadCart()

        binding.btnCheckout.setOnClickListener {
            val cartList = PerfumeRepository.getCartItems()
            if (cartList.isNotEmpty()) {
                cartList.forEach {
                    it.inCart = false
                    it.cartQuantity = 0
                }
                loadCart()
                Snackbar.make(binding.root, getString(R.string.order_success), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(
            items = emptyList(),
            onQuantityChanged = { perfume, newQty ->
                perfume.cartQuantity = newQty
                loadCart()
            },
            onRemoveItem = { perfume ->
                perfume.inCart = false
                perfume.cartQuantity = 0
                loadCart()
                Snackbar.make(binding.root, getString(R.string.removed_from_cart), Snackbar.LENGTH_SHORT).show()
            }
        )

        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = adapter
    }

    private fun loadCart() {
        val cartItems = PerfumeRepository.getCartItems()
        adapter.updateData(cartItems)

        if (cartItems.isEmpty()) {
            binding.emptyCartState.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
            binding.cardSummary.visibility = View.GONE
        } else {
            binding.emptyCartState.visibility = View.GONE
            binding.rvCart.visibility = View.VISIBLE
            binding.cardSummary.visibility = View.VISIBLE

            calculateTotals(cartItems)
        }
    }

    private fun calculateTotals(items: List<Perfume>) {
        val subtotal = items.sumOf { it.price * it.cartQuantity }
        val vat = subtotal * 0.15
        val total = subtotal + vat

        val currencyStr = getString(R.string.currency)
        binding.txtSubtotal.text = String.format(Locale.US, "%.2f %s", subtotal, currencyStr)
        binding.txtVat.text = String.format(Locale.US, "%.2f %s", vat, currencyStr)
        binding.txtTotal.text = String.format(Locale.US, "%.2f %s", total, currencyStr)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
