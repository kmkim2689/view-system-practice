package com.practice.view_system_practice.coroutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VmScopeDemoViewModel: ViewModel() {

    private val userRepository = UserRepository()

    /*var users: MutableLiveData<List<User>?> = MutableLiveData()
    fun getUserData() {
        viewModelScope.launch {
            var result: List<User>? = null
            // background에서 오래 걸리는 작업을 진행시킴
            withContext(Dispatchers.IO) {
                result = userRepository.getUsers()
            }
            users.value = result
        }
    }*/


    // livedata builder
    var users = liveData(Dispatchers.IO) {
        // liveData builder함수의 매개변수로 livedatascope를 받음.
        // livedata의 값을 set하기 위한 작업이 오래 걸리는(네트워크, 데이터베이스 등) 작업일 때 활용
        val result = userRepository.getUsers()
        // emit()를 활용하여 set -> 없으면 오류
        emit(result)
    }
}