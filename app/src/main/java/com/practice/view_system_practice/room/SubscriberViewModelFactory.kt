package com.practice.view_system_practice.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.room.database.SubscriberDatabase
import com.practice.view_system_practice.room.repository.SubscriberRepository

class SubscriberViewModelFactory(private val repository: SubscriberRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}