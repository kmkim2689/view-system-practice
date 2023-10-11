package com.practice.view_system_practice.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.Date

class UploadWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    companion object {
        const val KEY_WORKER = "key_worker"
    }

    override fun doWork(): Result {
        return try {
            // 외부로부터 request 당시 같이 온 데이터 꺼내쓰기 : inputData
            val count = inputData.getInt("key_count_value", 0)

            // 다시 외부로 보낼 outputData를 설정
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())

            val output = Data.Builder()
                .putString(KEY_WORKER, currentDate)
                .build()

            for (i in 0 until count) {
                Log.i("UploadWorker", "Uploading $i...")
            }

            Result.success(output)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}