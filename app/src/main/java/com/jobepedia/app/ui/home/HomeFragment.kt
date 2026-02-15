package com.jobepedia.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobepedia.app.R
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore



class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        val db = FirebaseFirestore.getInstance()

        db.collection("jobs").get()
            .addOnSuccessListener { result ->

                val jobList = mutableListOf<Job>()

                for (document in result) {

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


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.adapter = JobAdapter(dummyJobs) { job ->
//
//            val bundle = Bundle().apply {
//                putString("title", job.title)
//                putString("company", job.company)
//                putString("location", job.location)
//                putString("salary", job.salary)
//                putString("lastDate", job.lastDate)
//            }
//
//            findNavController().navigate(R.id.jobDetailFragment, bundle)
//        }

    }
}
