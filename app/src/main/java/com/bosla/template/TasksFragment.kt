package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosla.template.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotesAdapter(emptyList()) { item ->
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, DetailFragment.newInstance(item.id))
                .addToBackStack(null)
                .commit()
        }

        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = adapter

        refreshData()
    }

    fun refreshData() {
        val tasks = NotesRepository.getTasks()
        if (tasks.isEmpty()) {
            binding.rvTasks.visibility = View.GONE
            binding.layoutEmptyTasks.visibility = View.VISIBLE
        } else {
            binding.rvTasks.visibility = View.VISIBLE
            binding.layoutEmptyTasks.visibility = View.GONE
            adapter.updateData(tasks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
