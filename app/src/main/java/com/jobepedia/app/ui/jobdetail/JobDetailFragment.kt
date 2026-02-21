package com.jobepedia.app.ui.jobdetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
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
            openApplyLink(applyLink)
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

    private fun openApplyLink(rawLink: String) {
        if (rawLink.isBlank()) {
            Toast.makeText(requireContext(), R.string.invalid_apply_link, Toast.LENGTH_SHORT).show()
            return
        }

        val normalizedLink = if (rawLink.startsWith("http://") || rawLink.startsWith("https://")) {
            rawLink
        } else {
            "https://$rawLink"
        }

        val uri = Uri.parse(normalizedLink)
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()

        try {
            customTabsIntent.launchUrl(requireContext(), uri)
        } catch (_: ActivityNotFoundException) {
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            if (browserIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(browserIntent)
            } else {
                Toast.makeText(requireContext(), R.string.invalid_apply_link, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
