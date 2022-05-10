package com.bebrist.messagetowatch.models

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.bebrist.messagetowatch.R
import com.bebrist.messagetowatch.general.Constants
import com.bebrist.messagetowatch.general.Constants.CHANNEL_ID

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {

    }

    fun sendNotification(text: String) {
        Log.e(Constants.MAIN_LOG_TAG, text)

        val builder = NotificationCompat.Builder(getApplication(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_send_wh)
            .setContentTitle(getApplication<Application>().getString(R.string.notification_title))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val managerCompat = NotificationManagerCompat.from(getApplication())
        managerCompat.notify(0, builder.build())
    }

    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getApplication<Application>().getString(R.string.channel_name)
            val descriptionText = getApplication<Application>().getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getApplication<Application>().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}