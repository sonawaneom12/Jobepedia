package com.jobepedia.app.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.jobepedia.app.R
import com.jobepedia.app.databinding.FragmentSettingsBinding
import com.jobepedia.app.utils.UserPreferences

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        binding.darkModeSwitch.isChecked = UserPreferences.isDarkModeEnabled(requireContext())
        binding.notificationsSwitch.isChecked =
            UserPreferences.isPushNotificationsEnabled(requireContext())

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
