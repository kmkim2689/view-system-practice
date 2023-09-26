package com.practice.view_system_practice.room

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.view_system_practice.room.entity.Subscriber
import com.practice.view_system_practice.room.repository.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel() {

    // repository를 참고해보면, livedata 형태로 곧바로 가져오기 때문에 바로 액티비티에서 observe 가능
    val subscribers = repository.subscribers

    private var isEditMode = false
    private lateinit var subscriberBeingUpdated: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    // 버튼에 보여줄 텍스트도 상황에 따라 바뀌도록 할 것이므로, mutableLiveData로 정의
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    // wrapper 활용
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        // set initial display names of two buttons
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    // 클릭 동작 정의
    fun saveOrUpdate() {
        val name = inputName.value
        val email = inputEmail.value

        if (name == null) {
            statusMessage.value = Event("enter name")
        } else if (email == null) {
            statusMessage.value = Event("enter email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("enter the correct email")
        } else {
            if (isEditMode) {
                updateSubscriber(
                    subscriberBeingUpdated.copy(
                        name = name,
                        email = email
                    )
                )
            } else {
                insertSubscriber(Subscriber(0, name, email))
            }
            isEditMode = false
            inputName.value = ""
            inputEmail.value = ""
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
        }


    }

    // 클릭 동작 정의
    fun clearAllOrDelete() {
        if (isEditMode) {
            deleteSubscriber(subscriberBeingUpdated)
        } else {
            clearAll()
        }
    }

    fun insertSubscriber(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO) {
            val newItemId = repository.insertSubscriber(subscriber)
            withContext(Dispatchers.Main) {
                // verification by newly gotten id
                if (newItemId > -1) {
                    statusMessage.value = Event("Insert successful")
                } else {
                    statusMessage.value = Event("Insert Error")
                }
            }
        }

    private fun updateSubscriber(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO) {
            val numOfSubscribers = repository.updateSubscriber(subscriber)
            withContext(Dispatchers.Main) {
                if (numOfSubscribers > 0) {
                    statusMessage.value = Event("updated successful")
                } else {
                    statusMessage.value = Event("update error")
                }
            }
        }

    fun deleteSubscriber(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO) {
            val numOfDeleted = repository.deleteSubscriber(subscriber)
            withContext(Dispatchers.Main) {
                if (numOfDeleted > 0) {
                    inputName.value = ""
                    inputEmail.value = ""
                    isEditMode = false
                    saveOrUpdateButtonText.value = "Save"
                    clearAllOrDeleteButtonText.value = "Clear All"
                    statusMessage.value = Event("deleted successful")
                } else {
                    statusMessage.value = Event("delete error")
                }

            }
        }

    fun clearAll() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllSubscribers()
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("all of them deleted successful")
            }
        }

    fun initUpdateAndDelete(subscriber: Subscriber) =
        viewModelScope.launch {
            inputName.value = subscriber.name
            inputEmail.value = subscriber.email
            subscriberBeingUpdated = subscriber
            isEditMode = true
            saveOrUpdateButtonText.value = "Update"
            clearAllOrDeleteButtonText.value = "delete"
        }

}