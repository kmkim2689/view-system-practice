package com.practice.view_system_practice.navigation_mission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false)

        binding.btnSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_onboardingFragment_to_nameFragment)
        }

        binding.btnTerms.setOnClickListener {
            it.findNavController().navigate(R.id.action_onboardingFragment_to_termsFragment)
        }
        return binding.root
    }
}