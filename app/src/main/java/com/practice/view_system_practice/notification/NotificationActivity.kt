package com.practice.view_system_practice.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.practice.view_system_practice.R

class NotificationActivity : AppCompatActivity() {
    // to send a notification, first write codes to create a notification channel
    private val channelID = "com.practice.view_system_practice.channel1"
    private val KEY_REPLY = "key_reply"

    // define notification manager instance
    // -> required to create a notification channel + notification instance
    // define as var, as se get this manager using the system service. so there is a chance to be reassigned
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(channelID, "Demo", "this is a demo")
        val button = findViewById<Button>(R.id.btn_notification)

        button.setOnClickListener {
            displayNotification()
        }
    }

    @SuppressLint("PrivateResource")
    private fun displayNotification() {
        // to get the notification instance
        val notificationId = 12
        val defaultIntent = Intent(this@NotificationActivity, NotificationClickActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            defaultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action1 = Intent(this@NotificationActivity, NotificationDetailActivity::class.java)
        val pendingIntentForAction1 = PendingIntent.getActivity(
            this,
            0,
            action1,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val actionButton1 = NotificationCompat.Action.Builder(
            com.google.android.material.R.drawable.ic_keyboard_black_24dp,
            "detail",
            pendingIntentForAction1
        ).build()

        val action2 = Intent(this@NotificationActivity, NotificationSettingActivity::class.java)
        val pendingIntentForAction2 = PendingIntent.getActivity(
            this,
            0,
            action2,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val actionButton2 = NotificationCompat.Action.Builder(
            com.google.android.material.R.drawable.ic_keyboard_black_24dp,
            "setting",
            pendingIntentForAction2
        ).build()

        // remote input
        val remoteInput = RemoteInput.Builder(KEY_REPLY)
            .setLabel("your name")
            .build()

        val replyAction = NotificationCompat.Action.Builder(0, "REPLY", pendingIntent)
            .addRemoteInput(remoteInput)
            .build()

        val notification = NotificationCompat.Builder(this@NotificationActivity, channelID)
            .setContentTitle("demo title")
            .setContentText("this is a demo")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
            .addAction(actionButton1)
            .addAction(actionButton2)
            .addAction(replyAction)
            .build()
        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(
        id: String,
        name: String,
        channelDescription: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)
        }
    }
}