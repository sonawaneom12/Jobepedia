package com.jobepedia.app.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.jobepedia.app.R
import com.jobepedia.app.databinding.FragmentSettingsBinding
import com.jobepedia.app.utils.UserPreferences

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val experienceOptions = listOf("Fresher", "1-3 years", "3-5 years", "5+ years")
    private val frequencyOptions = listOf("Instant", "Daily", "Weekly")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        binding.darkModeSwitch.isChecked = UserPreferences.isDarkModeEnabled(requireContext())
        binding.notificationsSwitch.isChecked =
            UserPreferences.isPushNotificationsEnabled(requireContext())

        binding.experienceInput.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, experienceOptions)
        )
        binding.alertFrequencyInput.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, frequencyOptions)
        )

        binding.preferredCategoryInput.setText(UserPreferences.getPreferredCategory(requireContext()))
        binding.preferredLocationInput.setText(UserPreferences.getPreferredLocation(requireContext()))
        binding.minSalaryInput.setText(UserPreferences.getMinimumSalary(requireContext()))
        binding.experienceInput.setText(UserPreferences.getExperienceLevel(requireContext()), false)
        binding.alertFrequencyInput.setText(UserPreferences.getAlertFrequency(requireContext()), false)

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            UserPreferences.setDarkModeEnabled(requireContext(), isChecked)
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            UserPreferences.setPushNotificationsEnabled(requireContext(), isChecked)
            if (isChecked) {
                FirebaseMessaging.getInstance().subscribeToTopic("job_alerts")
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("job_alerts")
            }
        }

        binding.savePreferencesButton.setOnClickListener {
            UserPreferences.setPreferredCategory(
                requireContext(),
                binding.preferredCategoryInput.text?.toString().orEmpty().trim()
            )
            UserPreferences.setPreferredLocation(
                requireContext(),
                binding.preferredLocationInput.text?.toString().orEmpty().trim()
            )
            UserPreferences.setMinimumSalary(
                requireContext(),
                binding.minSalaryInput.text?.toString().orEmpty().trim()
            )
            UserPreferences.setExperienceLevel(
                requireContext(),
                binding.experienceInput.text?.toString().orEmpty().trim()
            )
            UserPreferences.setAlertFrequency(
                requireContext(),
                binding.alertFrequencyInput.text?.toString().orEmpty().trim()
            )

            Toast.makeText(requireContext(), R.string.settings_saved, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
