package com.practice.view_system_practice.navigation

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.FragmentHomeBinding
import java.time.Duration

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.btnSubmit.setOnClickListener {

            // null safety를 위하여 edittext에 text 존재 여부 검사
            if (!TextUtils.isEmpty(binding.etText.text.toString())) {
                // 다른 화면으로 넘겨줄 데이터
                // edittext의 content를 보내고자 함 -> bundle 형태로 만드는 것이 포인트
                // 하지만, 권장되지 않는 방법임.
                // viewModel을 사용하는 것이 best practice. 클릭 시 뷰모델에 데이터를 저장하고, 도착 fragment에서 viewmodel의 데이터를 사용
                val bundle = bundleOf(
                    "user_input" to binding.etText.text.toString()
                )

                // nav graph에서 정의한 action을 연결하기 위하여, navController 객체를 이용해야 한다.
                // 매개변수인 view(it)를 통해 navController를 찾아올 수 있음
                // navController의 navigate()함수를 통하여 화면 이동을 구현

                // 데이터를 넘겨주고자 한다면, navigate()의 두 번째 인자로 bundle을 넣는다.
                // 권장되지 않는 방법
                it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment, bundle)
                // it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
            } else {
                Toast.makeText(requireActivity(), "please insert text", LENGTH_SHORT).apply {
                    show()
                }
            }
        }
        return binding.root
    }

}