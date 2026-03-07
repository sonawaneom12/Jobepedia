package com.jobepedia.app.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jobepedia.app.R
import com.jobepedia.app.data.mappers.JobMapper
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobsQuery: Query

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val selectedCategory = arguments?.getString("category")
        jobsQuery = FirebaseFirestore.getInstance().collection("jobs").let { baseQuery ->
            if (selectedCategory != null) {
                baseQuery.whereEqualTo("category", selectedCategory)
            } else {
                baseQuery
            }
        }

        binding.swipeRefresh.setOnRefreshListener { loadJobs() }

        binding.swipeRefresh.isRefreshing = true
        loadJobs()
    }

    private fun loadJobs() {
        jobsQuery
            .get()
            .addOnSuccessListener { result ->
                val safeBinding = _binding ?: return@addOnSuccessListener
                val jobList = result.documents.map(JobMapper::fromDocument)
                safeBinding.recyclerView.adapter = JobAdapter(jobList) { job ->
                    findNavController().navigate(R.id.jobDetailFragment, job.toBundle())
                }
                safeBinding.swipeRefresh.isRefreshing = false
            }
            .addOnFailureListener { error ->
                _binding?.swipeRefresh?.isRefreshing = false
                Toast.makeText(
                    requireContext(),
                    error.localizedMessage ?: getString(R.string.unable_to_load_jobs),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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
            putString("jobHighlights", jobHighlights)
            putString("perksBenefits", perksBenefits)
            putString("applicationProcess", applicationProcess)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
