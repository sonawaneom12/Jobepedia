package com.jobepedia.app.ui.jobdetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jobepedia.app.R
import com.jobepedia.app.data.local.RecentlyViewedRepository
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.FragmentJobDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val jobHighlights = arguments?.getString("jobHighlights").orEmpty()
        val perksBenefits = arguments?.getString("perksBenefits").orEmpty()
        val applicationProcess = arguments?.getString("applicationProcess").orEmpty()

        binding.title.text = title
        binding.company.text = company
        binding.locationChip.text = location.ifBlank { getString(R.string.location_not_available) }
        binding.salaryChip.text = salary.ifBlank { getString(R.string.salary_not_disclosed) }
        binding.lastDateChip.text = getString(R.string.last_date, lastDate.ifBlank { "TBA" })
        binding.highlightText.text = getString(
            R.string.job_header_highlight,
            company.ifBlank { getString(R.string.company_details) }
        )

        binding.overviewDetails.text = listOf(
            getString(R.string.employment_type),
            getString(R.string.experience_required),
            getString(R.string.open_positions),
            getString(R.string.work_mode)
        ).joinToString("\n")

        binding.roleDetails.text = roleDetails.ifBlank { getString(R.string.role_details_fallback) }
        binding.companyDetails.text = companyDetails.ifBlank { getString(R.string.company_details_fallback) }
        val fallbackJobHighlights = getString(
            R.string.job_highlights_template,
            location.ifBlank { getString(R.string.location_not_available) },
            salary.ifBlank { getString(R.string.salary_not_disclosed) }
        )
        binding.jobHighlights.text = jobHighlights.ifBlank { fallbackJobHighlights }
        binding.perksDetails.text = perksBenefits.ifBlank { getString(R.string.perks_template) }
        binding.applicationProcessDetails.text = applicationProcess.ifBlank {
            getString(R.string.application_process_template)
        }

        Glide.with(this)
            .load(logoUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.companyLogo)

        binding.applyButton.setOnClickListener { openApplyLink(applyLink) }

        binding.shareButton.setOnClickListener {
            val shareText = getString(
                R.string.share_message_template,
                title,
                company,
                location.ifBlank { getString(R.string.location_not_available) },
                salary.ifBlank { getString(R.string.salary_not_disclosed) },
                getString(R.string.last_date, lastDate.ifBlank { "TBA" }),
                applyLink.ifBlank { getString(R.string.invalid_apply_link) }
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_job)))
        }

        binding.reportButton.setOnClickListener {
            Toast.makeText(requireContext(), R.string.report_received, Toast.LENGTH_SHORT).show()
        }

        trackRecentlyViewed(
            Job(
                title = title,
                company = company,
                location = location,
                salary = salary,
                lastDate = lastDate,
                logoUrl = logoUrl,
                roleDetails = roleDetails,
                companyDetails = companyDetails,
                applyLink = applyLink,
                jobHighlights = jobHighlights,
                perksBenefits = perksBenefits,
                applicationProcess = applicationProcess
            )
        )
    }

    private fun trackRecentlyViewed(job: Job) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            RecentlyViewedRepository.trackViewed(requireContext(), job)
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
        val customTabsIntent = CustomTabsIntent.Builder().setShowTitle(true).build()

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
