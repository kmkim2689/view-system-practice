package com.practice.view_system_practice.navigation_mission

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.FragmentNameBinding

class NameFragment : Fragment() {
    private lateinit var binding: FragmentNameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_name, container, false)

        binding.btnNext.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etName.text.toString())) {
                val bundle = bundleOf(
                    "name" to binding.etName.text.toString()
                )
                it.findNavController().navigate(R.id.action_nameFragment_to_emailFragment, bundle)

            } else {
                Toast.makeText(requireActivity(), "enter your name", Toast.LENGTH_SHORT).apply {
                    show()
                }
            }
        }

        return binding.root
    }

}