package com.jobepedia.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jobepedia.app.R
import com.jobepedia.app.utils.UserPreferences

class JobFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (!UserPreferences.isPushNotificationsEnabled(this)) {
            return
        }

        createChannelIfNeeded()

        val title = message.notification?.title ?: getString(R.string.new_job_alert)
        val body = message.notification?.body ?: getString(R.string.new_job_posted)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createChannelIfNeeded() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.job_alerts_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = getString(R.string.job_alerts_channel_description)
        }

        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "job_alerts"
    }
}
