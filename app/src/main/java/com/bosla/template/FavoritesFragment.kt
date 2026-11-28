package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosla.template.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PerfumeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadFavorites()
    }

    private fun setupRecyclerView() {
        adapter = PerfumeAdapter(
            items = emptyList(),
            onItemClick = {},
            onFavToggle = { perfume ->
                perfume.isFavorite = false
                loadFavorites()
                Snackbar.make(binding.root, "تمت الإزالة من المفضلة", Snackbar.LENGTH_SHORT).show()
            },
            onAddToCart = { perfume ->
                if (!perfume.inCart) {
                    perfume.inCart = true
                    perfume.cartQuantity = 1
                    Snackbar.make(binding.root, getString(R.string.added_to_cart), Snackbar.LENGTH_SHORT).show()
                } else {
                    perfume.inCart = false
                    perfume.cartQuantity = 0
                    Snackbar.make(binding.root, getString(R.string.removed_from_cart), Snackbar.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
            }
        )

        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = adapter
    }

    private fun loadFavorites() {
        val favs = PerfumeRepository.getFavorites()
        adapter.updateData(favs)
        if (favs.isEmpty()) {
            binding.emptyFavState.visibility = View.VISIBLE
            binding.rvFavorites.visibility = View.GONE
        } else {
            binding.emptyFavState.visibility = View.GONE
            binding.rvFavorites.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
