package com.jobepedia.app.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jobepedia.app.R
import com.jobepedia.app.data.model.Category
import com.jobepedia.app.databinding.FragmentCategoryBinding
import androidx.navigation.fragment.findNavController

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCategoryBinding.bind(view)

        val categories = listOf(
            Category("Government Jobs"),
            Category("Private Jobs"),
            Category("Exams"),
            Category("Internships"),
            Category("Results")
        )

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = CategoryAdapter(categories) { category ->

            val bundle = Bundle().apply {
                putString("category", category.name)
            }

            findNavController().navigate(R.id.homeFragment, bundle)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
