package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bosla.template.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PerfumeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateFavState()

        binding.btnEmptyFavHome.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }

    private fun setupRecyclerView() {
        adapter = PerfumeAdapter(
            items = emptyList(),
            onItemClick = { perfume ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DetailFragment.newInstance(perfume.id))
                    .addToBackStack(null)
                    .commit()
            },
            onFavoriteClick = { perfume ->
                PerfumeRepository.toggleFavorite(perfume.id)
                updateFavState()
                Snackbar.make(binding.root, getString(R.string.removed_from_fav), Snackbar.LENGTH_SHORT).show()
            },
            onCartClick = { perfume ->
                PerfumeRepository.toggleCart(perfume.id)
                updateFavState()
                val msg = if (perfume.isInCart) getString(R.string.added_to_cart) else getString(R.string.removed_from_cart)
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            }
        )
        binding.rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFavorites.adapter = adapter
    }

    private fun updateFavState() {
        val favorites = PerfumeRepository.getFavorites()
        adapter.updateData(favorites)

        if (favorites.isEmpty()) {
            binding.layoutEmptyFav.visibility = View.VISIBLE
            binding.rvFavorites.visibility = View.GONE
        } else {
            binding.layoutEmptyFav.visibility = View.GONE
            binding.rvFavorites.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
