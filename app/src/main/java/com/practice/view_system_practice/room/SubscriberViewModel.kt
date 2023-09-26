package com.practice.view_system_practice.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.view_system_practice.room.entity.Subscriber
import com.practice.view_system_practice.room.repository.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel() {

    // repository를 참고해보면, livedata 형태로 곧바로 가져오기 때문에 바로 액티비티에서 observe 가능
    val subscribers = repository.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    // 버튼에 보여줄 텍스트도 상황에 따라 바뀌도록 할 것이므로, mutableLiveData로 정의
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        // set initial display names of two buttons
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    // 클릭 동작 정의
    fun saveOrUpdate() {
        val name = inputName.value ?: ""
        val email = inputEmail.value ?: ""
        insertSubscriber(Subscriber(0, name, email))
        inputName.value = ""
        inputEmail.value = ""
    }

    // 클릭 동작 정의
    fun clearAllOrDelete() {
        clearAll()
    }

    fun insertSubscriber(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubscriber(subscriber)
        }

    fun updateSubscriber(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubscriber(subscriber)
        }

    fun deleteSubscriber(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSubscriber(subscriber)
        }

    fun clearAll() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllSubscribers()
        }



}