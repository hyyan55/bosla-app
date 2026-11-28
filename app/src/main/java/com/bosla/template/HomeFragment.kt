package com.bosla.template

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bosla.template.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PerfumeAdapter
    private var selectedCategory = "الكل"
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchAndFilter()
        filterData()
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
                filterData()
                val msg = if (perfume.isFavorite) getString(R.string.added_to_fav) else getString(R.string.removed_from_fav)
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            },
            onCartClick = { perfume ->
                PerfumeRepository.toggleCart(perfume.id)
                filterData()
                val msg = if (perfume.isInCart) getString(R.string.added_to_cart) else getString(R.string.removed_from_cart)
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            }
        )

        binding.rvPerfumes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPerfumes.adapter = adapter
    }

    private fun setupSearchAndFilter() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s.toString().trim()
                filterData()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedCategory = when {
                checkedIds.contains(R.id.chip_oud) -> getString(R.string.category_oud)
                checkedIds.contains(R.id.chip_musk) -> getString(R.string.category_musk)
                checkedIds.contains(R.id.chip_floral) -> getString(R.string.category_floral)
                else -> getString(R.string.category_all)
            }
            filterData()
        }
    }

    private fun filterData() {
        val all = PerfumeRepository.getAll()
        val filtered = all.filter { perfume ->
            val matchesCategory = (selectedCategory == getString(R.string.category_all) || perfume.category == selectedCategory)
            val matchesSearch = perfume.name.contains(searchQuery, ignoreCase = true) || perfume.description.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }

        adapter.updateData(filtered)
        if (filtered.isEmpty()) {
            binding.layoutEmpty.visibility = View.VISIBLE
            binding.rvPerfumes.visibility = View.GONE
        } else {
            binding.layoutEmpty.visibility = View.GONE
            binding.rvPerfumes.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
