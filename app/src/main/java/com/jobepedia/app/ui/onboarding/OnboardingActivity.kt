package com.jobepedia.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jobepedia.app.MainActivity
import com.jobepedia.app.R
import com.jobepedia.app.databinding.ActivityOnboardingBinding
import com.jobepedia.app.utils.UserPreferences

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pages = listOf(
            OnboardingPage(
                title = getString(R.string.onboarding_search_title),
                description = getString(R.string.onboarding_search_desc),
                iconRes = R.drawable.ic_search_24
            ),
            OnboardingPage(
                title = getString(R.string.onboarding_alerts_title),
                description = getString(R.string.onboarding_alerts_desc),
                iconRes = R.drawable.ic_settings_24
            ),
            OnboardingPage(
                title = getString(R.string.onboarding_apply_title),
                description = getString(R.string.onboarding_apply_desc),
                iconRes = R.drawable.ic_home_24
            )
        )

        binding.viewPager.adapter = OnboardingPagerAdapter(pages)

        TabLayoutMediator(binding.dotsIndicator, binding.viewPager) { _, _ -> }.attach()

        binding.skipButton.setOnClickListener { finishOnboarding() }
        binding.nextButton.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current == pages.lastIndex) {
                finishOnboarding()
            } else {
                binding.viewPager.currentItem = current + 1
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.nextButton.text = if (position == pages.lastIndex) {
                    getString(R.string.get_started)
                } else {
                    getString(R.string.next)
                }
            }
        })
    }

    private fun finishOnboarding() {
        UserPreferences.setOnboardingCompleted(this, true)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
