package com.practice.view_system_practice.notification

import android.app.RemoteInput
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.practice.view_system_practice.R

class NotificationClickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_click)
        receiveInput()
    }

    private fun receiveInput() {
        val intent = intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        remoteInput?.let {
            val inputString = it.getCharSequence("key_reply").toString()
            findViewById<TextView>(R.id.text_view3).text = inputString
        }
    }
}