package com.practice.view_system_practice.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityRoomDemoBinding
import com.practice.view_system_practice.room.database.SubscriberDatabase
import com.practice.view_system_practice.room.repository.SubscriberRepository

class RoomDemoActivity : AppCompatActivity() {

    companion object {
        const val TAG = "RoomDemoActivity"
    }

    private lateinit var binding: ActivityRoomDemoBinding
    private lateinit var viewModel: SubscriberViewModel
    private lateinit var database: SubscriberDatabase
    private lateinit var repository: SubscriberRepository
    private lateinit var adapter: SubscriberRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_demo)
        database = SubscriberDatabase.getInstance(this)
        repository = SubscriberRepository(database)


        val viewModelFactory = SubscriberViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SubscriberViewModel::class.java]


        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.rvSubscriber.layoutManager = LinearLayoutManager(this@RoomDemoActivity)
        displaySubscriberList()
    }

    private fun displaySubscriberList() {
        viewModel.subscribers.observe(this@RoomDemoActivity) {
            Log.i(TAG, it.toString())
            adapter = SubscriberRVAdapter(it)
            binding.rvSubscriber.adapter = adapter
        }
    }
}