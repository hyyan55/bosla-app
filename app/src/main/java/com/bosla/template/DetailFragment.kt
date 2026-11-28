package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bosla.template.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var itemId: String? = null

    companion object {
        private const val ARG_ITEM_ID = "item_id"

        fun newInstance(id: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(ARG_ITEM_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemId = arguments?.getString(ARG_ITEM_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = itemId?.let { NotesRepository.getItemById(it) }

        if (item == null) {
            parentFragmentManager.popBackStack()
            return
        }

        binding.tvDetailTitle.text = item.title
        binding.tvDetailDesc.text = item.description
        binding.tvCategoryBadge.text = item.category
        binding.tvDetailDate.text = item.date

        if (item.priority) {
            binding.tvUrgentBadge.visibility = View.VISIBLE
        } else {
            binding.tvUrgentBadge.visibility = View.GONE
        }

        if (item.isTask) {
            binding.btnToggleStatus.visibility = View.VISIBLE
            updateToggleButtonText(item.isCompleted)
        } else {
            binding.btnToggleStatus.visibility = View.GONE
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnToggleStatus.setOnClickListener {
            NotesRepository.toggleComplete(item.id)
            updateToggleButtonText(item.isCompleted)
            Snackbar.make(binding.root, R.string.msg_status_updated, Snackbar.LENGTH_SHORT).show()
        }

        binding.btnDelete.setOnClickListener {
            NotesRepository.deleteItem(item.id)
            Snackbar.make(binding.root, R.string.msg_deleted, Snackbar.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    private fun updateToggleButtonText(isCompleted: Boolean) {
        if (isCompleted) {
            binding.btnToggleStatus.text = getString(R.string.status_done)
        } else {
            binding.btnToggleStatus.text = getString(R.string.btn_toggle_complete)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
