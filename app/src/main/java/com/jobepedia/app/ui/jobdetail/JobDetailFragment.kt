package com.jobepedia.app.ui.jobdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jobepedia.app.R
import com.jobepedia.app.databinding.FragmentJobDetailBinding

class JobDetailFragment : Fragment(R.layout.fragment_job_detail) {

    private var _binding: FragmentJobDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentJobDetailBinding.bind(view)

        binding.title.text = arguments?.getString("title")
        binding.company.text = arguments?.getString("company")
        binding.location.text = arguments?.getString("location")
        binding.salary.text = arguments?.getString("salary")
        binding.lastDate.text = arguments?.getString("lastDate")
    }
}
