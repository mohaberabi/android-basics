package com.example.someimportantandroidtopics

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import com.example.someimportantandroidtopics.services.RunningTrackerServices

class ImprotantTopicsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                RunningTrackerServices.channelName,
                RunningTrackerServices.channelName,
                NotificationManager.IMPORTANCE_HIGH,
            )
            val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notiManager.createNotificationChannel(channel)
        }
    }

}