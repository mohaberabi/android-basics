package com.example.kotlinflows.flowoperator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

class OperatorsFlowViewModel : ViewModel() {


    private val flow = flow {

        val initVal = 100

        var currentVal = initVal
        emit(currentVal)
        while (currentVal > 0) {
            delay(1000)
            currentVal--
            emit(currentVal)
        }

    }


    var counter by mutableStateOf(100)


    private fun collectFlow() {
        viewModelScope.launch {

            flow.filter { count ->
                count % 2 == 0
            }.reduce { acc, value ->
                acc + value
            }

        }
    }

    init {
        collectFlow()
    }
}