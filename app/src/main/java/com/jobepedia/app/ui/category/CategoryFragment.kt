package com.jobepedia.app.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.jobepedia.app.R
import com.jobepedia.app.data.model.Category
import com.jobepedia.app.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter

    private val categoryNames = listOf(
        "Government Jobs",
        "Private Jobs",
        "Exams",
        "Internships",
        "Results"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCategoryBinding.bind(view)

        adapter = CategoryAdapter(categoryNames.map { Category(it, 0) }) { category ->
            val bundle = Bundle().apply {
                putString("category", category.name)
            }
            findNavController().navigate(R.id.homeFragment, bundle)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        loadCategoryCounts()
    }

    private fun loadCategoryCounts() {
        FirebaseFirestore.getInstance()
            .collection("jobs")
            .get()
            .addOnSuccessListener { snapshot ->
                val counts = snapshot.documents
                    .mapNotNull { it.getString("category") }
                    .groupingBy { it }
                    .eachCount()

                val categories = categoryNames.map { name ->
                    Category(name, counts[name] ?: 0)
                }

                if (_binding != null) {
                    adapter.updateData(categories)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
