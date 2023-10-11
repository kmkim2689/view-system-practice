package com.practice.view_system_practice.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.practice.view_system_practice.R
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {

    companion object {
        const val KEY_COUNT_VALUE = "key_count_value"
    }

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

        val workManager = WorkManager.getInstance(applicationContext)

        val data = Data.Builder()
            .putInt(KEY_COUNT_VALUE, 125)
            .build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // codes to tell the workmanager to perform the task
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val filteringRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java).build()
        val compressingRequest = OneTimeWorkRequest.Builder(CompressingWorker::class.java).build()
        val downloadingRequest = OneTimeWorkRequest.Builder(DownloadingWorker::class.java).build()

        val parallelWorkers = mutableListOf<OneTimeWorkRequest>(
            compressingRequest,
            downloadingRequest
        )

        // parallel chaining
/*        workManager
            .beginWith(parallelWorkers)
            .enqueue()*/

        // sequential chaining
        workManager
            .beginWith(filteringRequest)
            .then(compressingRequest)
            .then(uploadRequest)
            // 단일 worker만 사용할 때 enqueue 내부에 request 객체를 넣는다.
            // .enqueue(uploadRequest)
            .enqueue() // 만약 chain을 한다면, enqueue에는 아무 request도 넣지 않는다.



        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this) {
                val state = it.state.name
                if (it.state.isFinished) {
                    val outputData = it.outputData
                    val message = outputData.getString("key_worker")
                }
            }
    }

    private fun setPeriodicWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)

        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(DownloadingWorker::class.java, 16, TimeUnit.MINUTES)
            .build()

        workManager.enqueue(periodicWorkRequest)

    }
}