package com.jobepedia.app.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobepedia.app.R
import com.jobepedia.app.databinding.FragmentBookmarkBinding
import com.jobepedia.app.ui.home.JobAdapter
import com.jobepedia.app.utils.BookmarkManager

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBookmarkBinding.bind(view)

        val savedJobs = BookmarkManager.getSavedJobs(requireContext())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = JobAdapter(savedJobs) {}
    }
}
