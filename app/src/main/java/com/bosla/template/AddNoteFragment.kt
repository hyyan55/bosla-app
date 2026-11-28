package com.bosla.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bosla.template.databinding.FragmentAddNoteBinding
import com.google.android.material.snackbar.Snackbar
import java.util.UUID

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackAdd.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etAddTitle.text.toString().trim()
            val desc = binding.etAddDesc.text.toString().trim()

            if (title.isEmpty() || desc.isEmpty()) {
                Snackbar.make(binding.root, R.string.msg_fill_fields, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isTask = binding.rbTask.isChecked
            val isUrgent = binding.cbUrgent.isChecked

            val category = when {
                binding.chipAddWork.isChecked -> "العمل"
                binding.chipAddPersonal.isChecked -> "شخصي"
                else -> "دراسة"
            }

            val newItem = NoteItem(
                id = UUID.randomUUID().toString(),
                title = title,
                description = desc,
                category = category,
                priority = isUrgent,
                date = getString(R.string.date_today),
                isTask = isTask,
                isCompleted = false
            )

            NotesRepository.addItem(newItem)
            Snackbar.make(binding.root, R.string.msg_added, Snackbar.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
