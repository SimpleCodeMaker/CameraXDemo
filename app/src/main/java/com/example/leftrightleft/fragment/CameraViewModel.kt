package com.example.leftrightleft.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class CameraViewModel : ViewModel() {
    val scallCallBack = MutableLiveData<Any>()
    var job: Job?=null
    fun startScroll() {
        viewModelScope.launch {
            while (true){
                delay(32)
                scallCallBack.value = 0
            }
        }
//        val active = job.isActive
//        val cancelled = job.isCancelled
//        val completed = job.isCompleted
//        job?.start()
    }
}