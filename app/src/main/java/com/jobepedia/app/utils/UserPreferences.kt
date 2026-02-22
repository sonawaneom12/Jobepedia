package com.jobepedia.app.utils

import android.content.Context

object UserPreferences {

    private const val PREF_NAME = "user_preferences"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_PUSH_NOTIFICATIONS = "push_notifications"
    private const val KEY_PREFERRED_CATEGORY = "preferred_category"
    private const val KEY_PREFERRED_LOCATION = "preferred_location"
    private const val KEY_MINIMUM_SALARY = "minimum_salary"
    private const val KEY_EXPERIENCE_LEVEL = "experience_level"
    private const val KEY_ALERT_FREQUENCY = "alert_frequency"

    fun isDarkModeEnabled(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkModeEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_DARK_MODE, enabled)
            .apply()
    }

    fun isPushNotificationsEnabled(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_PUSH_NOTIFICATIONS, true)
    }

    fun setPushNotificationsEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_PUSH_NOTIFICATIONS, enabled)
            .apply()
    }

    fun getPreferredCategory(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_PREFERRED_CATEGORY, "")
            .orEmpty()
    }

    fun setPreferredCategory(context: Context, value: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_PREFERRED_CATEGORY, value)
            .apply()
    }

    fun getPreferredLocation(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_PREFERRED_LOCATION, "")
            .orEmpty()
    }

    fun setPreferredLocation(context: Context, value: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_PREFERRED_LOCATION, value)
            .apply()
    }

    fun getMinimumSalary(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_MINIMUM_SALARY, "")
            .orEmpty()
    }

    fun setMinimumSalary(context: Context, value: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_MINIMUM_SALARY, value)
            .apply()
    }

    fun getExperienceLevel(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_EXPERIENCE_LEVEL, "Fresher")
            .orEmpty()
    }

    fun setExperienceLevel(context: Context, value: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_EXPERIENCE_LEVEL, value)
            .apply()
    }

    fun getAlertFrequency(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_ALERT_FREQUENCY, "Instant")
            .orEmpty()
    }

    fun setAlertFrequency(context: Context, value: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_ALERT_FREQUENCY, value)
            .apply()
    }
}
