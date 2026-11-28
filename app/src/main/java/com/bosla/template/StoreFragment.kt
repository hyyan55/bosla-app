package com.bosla.template

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosla.template.databinding.DialogPerfumeDetailBinding
import com.bosla.template.databinding.FragmentStoreBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PerfumeAdapter
    private var selectedCategory: String = "الكل"
    private var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
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
            onItemClick = { perfume -> showDetailDialog(perfume) },
            onFavToggle = { perfume ->
                perfume.isFavorite = !perfume.isFavorite
                adapter.notifyDataSetChanged()
                val msg = if (perfume.isFavorite) "أضيف للمفضلة" else "أزيل من المفضلة"
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
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

        binding.rvPerfumes.layoutManager = LinearLayoutManager(requireContext())
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

        binding.chipGroupCategories.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            selectedCategory = chip?.text?.toString() ?: getString(R.string.cat_all)
            filterData()
        }
    }

    private fun filterData() {
        val filtered = PerfumeRepository.perfumesList.filter { perfume ->
            val matchesCategory = selectedCategory == getString(R.string.cat_all) || perfume.category == selectedCategory
            val matchesSearch = perfume.name.contains(searchQuery, ignoreCase = true) ||
                                perfume.brand.contains(searchQuery, ignoreCase = true) ||
                                perfume.notes.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }

        adapter.updateData(filtered)
        binding.txtEmptyStore.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun showDetailDialog(perfume: Perfume) {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogPerfumeDetailBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        with(dialogBinding) {
            txtDetailName.text = perfume.name
            txtDetailBrandCategory.text = "${perfume.brand} • ${perfume.category}"
            txtDetailPrice.text = "${perfume.price} ${getString(R.string.currency)}"
            txtDetailNotes.text = perfume.notes
            txtDetailDesc.text = perfume.description

            btnDetailAddToCart.text = if (perfume.inCart) {
                getString(R.string.in_cart)
            } else {
                getString(R.string.add_to_cart)
            }

            btnDetailAddToCart.setOnClickListener {
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
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
