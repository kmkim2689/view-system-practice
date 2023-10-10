package com.practice.view_system_practice.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.practice.view_system_practice.R

class WorkManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
        val btn = findViewById<Button>(R.id.btn_enqueue)
        val tv = findViewById<TextView>(R.id.tv_result_work)

        btn.setOnClickListener {
            setOneTimeWorkRequest()
        }
    }

    private fun setOneTimeWorkRequest() {
        // codes to tell the workmanager to perform the task
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(uploadRequest)
    }
}