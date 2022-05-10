package com.bebrist.messagetowatch.models

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bebrist.messagetowatch.R
import com.bebrist.messagetowatch.general.Constants
import com.bebrist.messagetowatch.general.Constants.CHANNEL_ID

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var currentText: String = ""

    fun sendNotification() {

        Log.e(Constants.MAIN_LOG_TAG, currentText)

        /**
         *  На многих smart часах уведомление сокращается до 90 символов.
         *  Выполняется проверка на длину текста, чтобы он показывался полностью.
         **/
        if (currentText.length <= 90 && currentText != "") {
            val builder = NotificationCompat.Builder(getApplication(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(getApplication<Application>().getString(R.string.notification_title))
                .setContentText(currentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            val managerCompat = NotificationManagerCompat.from(getApplication())
            managerCompat.notify(0, builder.build())
        }
    }

    // Создание канала уведомлений (требуется для новых версий Android)
    fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getApplication<Application>().getString(R.string.channel_name)
            val descriptionText =
                getApplication<Application>().getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getApplication<Application>().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}