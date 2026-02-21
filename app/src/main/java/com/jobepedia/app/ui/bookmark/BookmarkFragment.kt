package com.jobepedia.app.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadBookmarks()
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) {
            loadBookmarks()
        }
    }

    private fun loadBookmarks() {
        val savedJobs = BookmarkManager.getSavedJobs(requireContext())
        binding.recyclerView.adapter = JobAdapter(savedJobs) { job ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
