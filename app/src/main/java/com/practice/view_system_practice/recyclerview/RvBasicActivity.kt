package com.practice.view_system_practice.recyclerview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityRvBasicBinding
import kotlinx.coroutines.selects.select

class RvBasicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRvBasicBinding
    private lateinit var rvTest: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rv_basic)
        rvTest = binding.rvTest

        val data = listOf(
            TestItem("apple", "joe"),
            TestItem("banana", "frank"),
            TestItem("e", "joe"),
            TestItem("f", "joe"),
            TestItem("g", "joe"),
            TestItem("h", "joe"),
            TestItem("oi", "joe"),
            TestItem("j", "joe"),
        )

        rvTest.apply {
            setBackgroundColor(Color.YELLOW)
            layoutManager = LinearLayoutManager(this@RvBasicActivity)
            adapter = TestRVAdapter(data) { selectedItem: TestItem ->
                listItemClicked(selectedItem)
            }
        }
    }

    // 선택된 아이템의 객체 데이터를 가져오고 토스트 메시지를 출력
    private fun listItemClicked(item: TestItem) {
        item.name = "kmkim"
        Toast.makeText(
            this@RvBasicActivity,
            "${item.name} has been clicked, supplier is ${item.supplier}",
            Toast.LENGTH_SHORT
        ).show()
    }
}