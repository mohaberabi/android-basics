package com.example.kotlinflows.simpleflow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {


    private val countDownFlow = flow {

        val initVal = 10

        var currentVal = initVal
        emit(currentVal)
        while (currentVal > 0) {
            delay(1000)
            currentVal--
            emit(currentVal)
        }

    }


    private val countDownFlowToBeCollectAsLatest = flow {

        val initVal = 10

        var currentVal = initVal
        emit(currentVal)
        while (currentVal > 0) {
            delay(1000)
            currentVal--
            emit(currentVal)
        }

    }

    var counter by mutableStateOf(10)

    var counterAsLatest by mutableStateOf(10)

    private fun collectFlow() {
        viewModelScope.launch {
            countDownFlow.collect {
                counter = it
            }
        }
    }

    private fun collectFlowAsLatest() {
        viewModelScope.launch {
            countDownFlowToBeCollectAsLatest.collectLatest {

                delay(1500)
                counterAsLatest = it
            }
        }
    }

    init {
        collectFlow()
        collectFlowAsLatest()
    }
}