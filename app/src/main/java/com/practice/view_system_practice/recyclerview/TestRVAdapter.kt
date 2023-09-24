package com.practice.view_system_practice.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ItemRvTestBinding

class TestRVAdapter(
    private val list: List<TestItem>,
    private val onItemClick: (TestItem) -> Unit
): RecyclerView.Adapter<TestRVAdapter.TestViewHolder>() {
    // 데이터에 대한 리스트는 view controller에서 관리하는 것이 good convention -> Activity/Fragment
    inner class TestViewHolder(
        val view: View,
        val binding: ItemRvTestBinding
    ): RecyclerView.ViewHolder(binding.root) {
        // write codes to get the view components in the list item layout using fvbi
        // val tvTestById = view.findViewById<TextView>(R.id.tv_rv_test)

        // get the view component in the list item by using databinding
        private val tvTest = binding.tvRvTest
        fun bind(item: TestItem) {
            tvTest.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // for fvbi
        val listItem = layoutInflater.inflate(R.layout.item_rv_test, parent, false)

        // for viewBinding
        val viewBinding = ItemRvTestBinding.inflate(
            layoutInflater,
            parent,
            false
        )

        // for dataBinding
        val binding = DataBindingUtil.inflate<ItemRvTestBinding>(
            layoutInflater,
            R.layout.item_rv_test,
            parent,
            false
        )

        return TestViewHolder(listItem, binding).also { holder -> // viewHolder
            // holder.itemView.setOnClickListener {}
            binding.tvRvTest.setOnClickListener {
                // 데이터의 리스트를 바꾸고자 하는데, 인덱스를 가져오고자 함.
                // onBindViewHolder에서는 position 매개변수가 넘어오기 때문에 position을 그대로 활용하면 되지만,
                // onCreateViewHolder에서는 ViewHolder 객체 내부의 getter를 활용하여 position을 가져온다.
                onItemClick(list[holder.adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        // onCreateViewHolder()에서 제작하였던 view로부터의 인스턴스
        holder.bind(list[position])
        // holder.adapterPosition -> 부모 rv가 없으면 -1을 반환
        holder.itemView
    }

    override fun getItemCount(): Int {
        return list.size
    }
}