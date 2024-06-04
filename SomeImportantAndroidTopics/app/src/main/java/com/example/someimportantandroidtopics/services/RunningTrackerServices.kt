package com.example.someimportantandroidtopics.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.someimportantandroidtopics.R

class RunningTrackerServices() : Service() {
    companion object {
        const val channelName = "running-channel"
    }

    override fun onBind(
        intent:
        Intent?
    ): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val noti = NotificationCompat
            .Builder(this, channelName)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("You are now running")
            .setContentText("Elapsed time: 00:50")
            .build()
        startForeground(1, noti)
    }

    enum class Actions {
        START, STOP
    }
}