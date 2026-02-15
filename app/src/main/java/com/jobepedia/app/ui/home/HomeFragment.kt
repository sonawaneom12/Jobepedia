package com.jobepedia.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobepedia.app.R
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        val dummyJobs = listOf(
            Job("Software Engineer", "Google", "Bangalore", "₹20 LPA", "30 Mar"),
            Job("Android Developer", "Amazon", "Hyderabad", "₹15 LPA", "10 Apr"),
            Job("Backend Developer", "Microsoft", "Remote", "₹18 LPA", "5 Apr")
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = JobAdapter(dummyJobs)
    }
}
