package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosla.template.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateCartState()

        binding.btnEmptyCartHome.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        binding.btnCheckout.setOnClickListener {
            if (PerfumeRepository.getCart().isNotEmpty()) {
                PerfumeRepository.clearCart()
                updateCartState()
                Snackbar.make(binding.root, getString(R.string.checkout_success), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(emptyList()) { perfume ->
            PerfumeRepository.toggleCart(perfume.id)
            updateCartState()
            Snackbar.make(binding.root, getString(R.string.removed_from_cart), Snackbar.LENGTH_SHORT).show()
        }
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = adapter
    }

    private fun updateCartState() {
        val cartItems = PerfumeRepository.getCart()
        adapter.updateData(cartItems)

        if (cartItems.isEmpty()) {
            binding.layoutEmptyCart.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
            binding.checkoutCard.visibility = View.GONE
        } else {
            binding.layoutEmptyCart.visibility = View.GONE
            binding.rvCart.visibility = View.VISIBLE
            binding.checkoutCard.visibility = View.VISIBLE
            
            val total = PerfumeRepository.getCartTotal()
            binding.tvCartTotal.text = "${total.toInt()} ${getString(R.string.currency)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
