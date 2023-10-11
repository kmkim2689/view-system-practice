package com.practice.view_system_practice.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FilteringWorker(
    private val context: Context,
    private val params: WorkerParameters
): Worker(context, params) {
    override fun doWork(): Result {
        return try {
            for (i in 0..300) {
                Log.i("FilteringWorker", "filtering $i")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}