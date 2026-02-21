package com.jobepedia.app.ui.jobdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jobepedia.app.R
import com.jobepedia.app.databinding.FragmentJobDetailBinding

class JobDetailFragment : Fragment(R.layout.fragment_job_detail) {

    private var _binding: FragmentJobDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentJobDetailBinding.bind(view)

        val title = arguments?.getString("title").orEmpty()
        val company = arguments?.getString("company").orEmpty()
        val location = arguments?.getString("location").orEmpty()
        val salary = arguments?.getString("salary").orEmpty()
        val lastDate = arguments?.getString("lastDate").orEmpty()
        val logoUrl = arguments?.getString("logoUrl").orEmpty()
        val roleDetails = arguments?.getString("roleDetails").orEmpty()
        val companyDetails = arguments?.getString("companyDetails").orEmpty()
        val applyLink = arguments?.getString("applyLink").orEmpty()

        binding.title.text = title
        binding.company.text = company
        binding.location.text = location
        binding.salary.text = salary
        binding.lastDate.text = getString(R.string.last_date, lastDate)
        binding.roleDetails.text = roleDetails
        binding.companyDetails.text = companyDetails

        Glide.with(this)
            .load(logoUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.companyLogo)

        binding.applyButton.setOnClickListener {
            if (applyLink.isBlank()) {
                Toast.makeText(requireContext(), R.string.invalid_apply_link, Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(applyLink)))
            }
        }

        binding.shareButton.setOnClickListener {
            val shareText = listOf(
                title,
                company,
                location,
                salary,
                getString(R.string.last_date, lastDate),
                applyLink
            ).joinToString("\n")

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_job)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
