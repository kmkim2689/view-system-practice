package com.practice.view_system_practice.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityRoomDemoBinding
import com.practice.view_system_practice.room.database.SubscriberDatabase
import com.practice.view_system_practice.room.entity.Subscriber
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

        viewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let { messageText ->
                Toast.makeText(this, messageText, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initRecyclerView() {
        binding.rvSubscriber.layoutManager = LinearLayoutManager(this@RoomDemoActivity)
        adapter = SubscriberRVAdapter { subscriber: Subscriber ->
            onListItemClick(subscriber)
        }
        binding.rvSubscriber.adapter = adapter
        displaySubscriberList()
    }

    private fun displaySubscriberList() {
        viewModel.subscribers.observe(this@RoomDemoActivity) {
            Log.i(TAG, it.toString())
            // 변화가 발생할 때마다, adapter 바깥에서 리스트의 값을 갱신
            adapter.setList(it)
            // 단순히 adapter 클래스 안에 있는 데이터에만 변화를 주었을 뿐, adapter는 상황을 알지 못함
            // 따라서 아래와 같이 adapter에 데이터 상 변화가 발생하였음을 알린다.
            adapter.notifyDataSetChanged()
        }
    }

    private fun onListItemClick(subscriber: Subscriber) {
        viewModel.initUpdateAndDelete(subscriber)
    }
}