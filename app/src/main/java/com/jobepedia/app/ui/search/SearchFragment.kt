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
                        title = it.getString("title") ?: "",
                        company = it.getString("company") ?: "",
                        location = it.getString("location") ?: "",
                        salary = it.getString("salary") ?: "",
                        lastDate = it.getString("lastDate") ?: "",
                        logoUrl = it.getString("logoUrl") ?: "",
                        roleDetails = it.getString("roleDetails") ?: "",
                        companyDetails = it.getString("companyDetails") ?: "",
                        applyLink = it.getString("applyLink") ?: ""
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
                    findNavController().navigate(R.id.jobDetailFragment, job.toBundle())
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

    private fun Job.toBundle(): Bundle {
        return Bundle().apply {
            putString("title", title)
            putString("company", company)
            putString("location", location)
            putString("salary", salary)
            putString("lastDate", lastDate)
            putString("logoUrl", logoUrl)
            putString("roleDetails", roleDetails)
            putString("companyDetails", companyDetails)
            putString("applyLink", applyLink)
        }
    }
}
