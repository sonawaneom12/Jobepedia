package com.jobepedia.app.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.jobepedia.app.R
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.FragmentSearchBinding
import com.jobepedia.app.ui.home.JobAdapter

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.searchButton.setOnClickListener {
            performSearch(binding.searchInput.text.toString())
        }
    }

    private fun performSearch(rawQuery: String) {
        val queryText = rawQuery.trim().lowercase()
        if (queryText.isBlank()) {
            binding.emptyState.isVisible = true
            binding.emptyState.setText(R.string.search_to_get_started)
            binding.recyclerView.adapter = JobAdapter(emptyList()) {}
            return
        }

        FirebaseFirestore.getInstance()
            .collection("jobs")
            .get()
            .addOnSuccessListener { result ->
                val jobs = result.documents.map {
                    Job(
                        it.getString("title") ?: "",
                        it.getString("company") ?: "",
                        it.getString("location") ?: "",
                        it.getString("salary") ?: "",
                        it.getString("lastDate") ?: ""
                    )
                }.filter {
                    it.title.lowercase().contains(queryText) ||
                        it.company.lowercase().contains(queryText) ||
                        it.location.lowercase().contains(queryText)
                }

                binding.emptyState.isVisible = jobs.isEmpty()
                if (jobs.isEmpty()) {
                    binding.emptyState.setText(R.string.no_jobs_found)
                }

                binding.recyclerView.adapter = JobAdapter(jobs) { job ->
                    val bundle = Bundle().apply {
                        putString("title", job.title)
                        putString("company", job.company)
                        putString("location", job.location)
                        putString("salary", job.salary)
                        putString("lastDate", job.lastDate)
                    }
                    findNavController().navigate(R.id.jobDetailFragment, bundle)
                }
            }
            .addOnFailureListener {
                binding.emptyState.isVisible = true
                binding.emptyState.text = it.localizedMessage ?: getString(R.string.no_jobs_found)
                binding.recyclerView.adapter = JobAdapter(emptyList()) {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
