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
    private var perfumeId: Int = -1

    companion object {
        private const val ARG_PERFUME_ID = "arg_perfume_id"
        fun newInstance(id: Int): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle().apply { putInt(ARG_PERFUME_ID, id) }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        perfumeId = arguments?.getInt(ARG_PERFUME_ID) ?: -1
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

        val perfume = PerfumeRepository.getAll().find { it.id == perfumeId }
        if (perfume == null) {
            parentFragmentManager.popBackStack()
            return
        }

        with(binding) {
            tvDetailName.text = perfume.name
            tvDetailCategory.text = perfume.category
            tvDetailRating.text = getString(R.string.rating_prefix) + perfume.rating.toString()
            tvDetailDescription.text = perfume.description
            tvDetailPrice.text = "${perfume.price.toInt()} ${getString(R.string.currency)}"

            updateButtonState(perfume)

            btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            btnDetailAddCart.setOnClickListener {
                PerfumeRepository.toggleCart(perfume.id)
                updateButtonState(perfume)
                val msg = if (perfume.isInCart) getString(R.string.added_to_cart) else getString(R.string.removed_from_cart)
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateButtonState(perfume: Perfume) {
        binding.btnDetailAddCart.text = if (perfume.isInCart) {
            getString(R.string.in_cart)
        } else {
            getString(R.string.add_to_cart)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
