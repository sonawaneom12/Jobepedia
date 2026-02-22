package com.jobepedia.app.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.jobepedia.app.R
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var jobsListener: ListenerRegistration? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val selectedCategory = arguments?.getString("category")
        val db = FirebaseFirestore.getInstance()
        var query: com.google.firebase.firestore.Query = db.collection("jobs")

        if (selectedCategory != null) {
            query = query.whereEqualTo("category", selectedCategory)
        }

        jobsListener?.remove()
        jobsListener = query.addSnapshotListener { result, error ->
            val safeBinding = _binding ?: return@addSnapshotListener

        query.addSnapshotListener { result, error ->
            if (error != null) {
                Toast.makeText(
                    requireContext(),
                    error.localizedMessage ?: "Unable to load jobs",
                    Toast.LENGTH_SHORT
                ).show()
                return@addSnapshotListener
            }

            val jobList = result?.documents?.map { document ->
                Job(
            val jobList = mutableListOf<Job>()

            result?.forEach { document ->
                val job = Job(
                    title = document.getString("title") ?: "",
                    company = document.getString("company") ?: "",
                    location = document.getString("location") ?: "",
                    salary = document.getString("salary") ?: "",
                    lastDate = document.getString("lastDate") ?: "",
                    logoUrl = document.getString("logoUrl") ?: "",
                    roleDetails = document.getString("roleDetails") ?: "",
                    companyDetails = document.getString("companyDetails") ?: "",
                    applyLink = document.getString("applyLink") ?: ""
                )
            }.orEmpty()

            safeBinding.recyclerView.adapter = JobAdapter(jobList) { job ->
                findNavController().navigate(R.id.jobDetailFragment, job.toBundle())
            }
        }
    }

    override fun onDestroyView() {
        jobsListener?.remove()
        jobsListener = null
        _binding = null
        super.onDestroyView()
            binding.recyclerView.adapter = JobAdapter(jobList) { job ->
                findNavController().navigate(R.id.jobDetailFragment, job.toBundle())
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
