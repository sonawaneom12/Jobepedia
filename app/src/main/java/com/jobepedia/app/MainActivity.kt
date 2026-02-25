package com.jobepedia.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.jobepedia.app.databinding.ActivityMainBinding
import com.jobepedia.app.utils.UserPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        applyThemeFromPreference()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val topLevelDestinations = setOf(
            R.id.homeFragment,
            R.id.categoryFragment,
            R.id.searchFragment,
            R.id.bookmarkFragment,
            R.id.settingsFragment
        )

        binding.bottomNav.setOnItemSelectedListener { item ->
            val targetId = item.itemId

            if (targetId == R.id.homeFragment) {
                val poppedToHome = navController.popBackStack(R.id.homeFragment, false)
                if (!poppedToHome) {
                    navController.navigate(
                        R.id.homeFragment,
                        null,
                        navOptions {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                        }
                    )
                }
                true
            } else {
                val options = navOptions {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }

                runCatching {
                    navController.navigate(targetId, null, options)
                }.isSuccess
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in topLevelDestinations) {
                binding.bottomNav.menu.findItem(destination.id)?.isChecked = true
            }

            binding.topAppBar.title = destination.label ?: getString(R.string.app_name)
            binding.topAppBar.subtitle = when (destination.id) {
                R.id.homeFragment -> getString(R.string.topbar_subtitle_home)
                R.id.categoryFragment -> getString(R.string.topbar_subtitle_categories)
                R.id.searchFragment -> getString(R.string.topbar_subtitle_search)
                R.id.bookmarkFragment -> getString(R.string.topbar_subtitle_saved)
                R.id.settingsFragment -> getString(R.string.topbar_subtitle_settings)
                else -> getString(R.string.topbar_subtitle_details)
            }

            supportActionBar?.setDisplayHomeAsUpEnabled(destination.id !in topLevelDestinations)
        }

        binding.topAppBar.setNavigationOnClickListener {
            if (!navController.popBackStack()) {
                finish()
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            val destinationId = navController.currentDestination?.id
            if (destinationId == R.id.homeFragment) {
                finish()
            } else {
                binding.bottomNav.selectedItemId = R.id.homeFragment
            }
        }

        updateTopicSubscription()
        requestNotificationPermissionIfNeeded()
    }

    private fun applyThemeFromPreference() {
        val mode = if (UserPreferences.isDarkModeEnabled(this)) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun updateTopicSubscription() {
        if (UserPreferences.isPushNotificationsEnabled(this)) {
            FirebaseMessaging.getInstance().subscribeToTopic("job_alerts")
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("job_alerts")
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1101
            )
        }
    }
}
