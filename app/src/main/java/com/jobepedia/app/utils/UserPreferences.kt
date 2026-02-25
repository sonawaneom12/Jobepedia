package com.jobepedia.app.utils

import android.content.Context

object UserPreferences {

    private const val PREF_NAME = "user_preferences"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_PUSH_NOTIFICATIONS = "push_notifications"

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
}
