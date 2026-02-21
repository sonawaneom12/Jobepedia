package com.jobepedia.app.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.jobepedia.app.R
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        query.addSnapshotListener { result, error ->

            if (error != null) {
                Toast.makeText(requireContext(), error.localizedMessage ?: "Unable to load jobs", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val jobList = mutableListOf<Job>()

            result?.forEach { document ->

                val job = Job(
                    document.getString("title") ?: "",
                    document.getString("company") ?: "",
                    document.getString("location") ?: "",
                    document.getString("salary") ?: "",
                    document.getString("lastDate") ?: ""
                )

                jobList.add(job)
            }

            binding.recyclerView.adapter = JobAdapter(jobList) { job ->

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
