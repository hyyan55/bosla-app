package com.bosla.template

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosla.template.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAdapter: NotesAdapter
    private lateinit var featuredAdapter: FeaturedAdapter

    private var currentCategory = "الكل"
    private var currentQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupListeners()
        refreshData()
    }

    private fun setupRecyclerViews() {
        featuredAdapter = FeaturedAdapter(emptyList()) { item ->
            openDetail(item.id)
        }
        binding.rvFeatured.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeatured.adapter = featuredAdapter

        mainAdapter = NotesAdapter(emptyList()) { item ->
            openDetail(item.id)
        }
        binding.rvMainList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMainList.adapter = mainAdapter
    }

    private fun setupListeners() {
        binding.btnHeroAction.setOnClickListener {
            openAddNote()
        }

        binding.fabAdd.setOnClickListener {
            openAddNote()
        }

        binding.btnResetEmpty.setOnClickListener {
            binding.etSearch.setText("")
            binding.chipAll.isChecked = true
            currentCategory = "الكل"
            filterData()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentQuery = s.toString()
                filterData()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.chipGroupCats.setOnCheckedStateChangeListener { _, checkedIds ->
            currentCategory = if (checkedIds.contains(R.id.chip_work)) {
                "العمل"
            } else if (checkedIds.contains(R.id.chip_personal)) {
                "شخصي"
            } else if (checkedIds.contains(R.id.chip_study)) {
                "دراسة"
            } else {
                "الكل"
            }
            filterData()
        }
    }

    fun refreshData() {
        val allItems = NotesRepository.getAllItems()
        val completedCount = allItems.count { it.isCompleted }
        val urgentCount = allItems.count { it.priority }

        binding.tvStatTotalVal.text = allItems.size.toString()
        binding.tvStatCompletedVal.text = completedCount.toString()
        binding.tvStatUrgentVal.text = urgentCount.toString()

        featuredAdapter.updateData(NotesRepository.getUrgentItems())
        filterData()
    }

    private fun filterData() {
        var filtered = NotesRepository.getAllItems()

        if (currentCategory != "الكل") {
            filtered = filtered.filter { it.category == currentCategory }
        }

        if (currentQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.title.contains(currentQuery, ignoreCase = true) ||
                        it.description.contains(currentQuery, ignoreCase = true)
            }
        }

        if (filtered.isEmpty()) {
            binding.rvMainList.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
        } else {
            binding.rvMainList.visibility = View.VISIBLE
            binding.layoutEmpty.visibility = View.GONE
            mainAdapter.updateData(filtered)
        }
    }

    private fun openDetail(itemId: String) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, DetailFragment.newInstance(itemId))
            .addToBackStack(null)
            .commit()
    }

    private fun openAddNote() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_container, AddNoteFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
