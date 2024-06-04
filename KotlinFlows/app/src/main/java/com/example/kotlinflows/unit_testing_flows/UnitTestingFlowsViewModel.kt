package com.example.kotlinflows.unit_testing_flows

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class UnitTestingFlowsViewModel : ViewModel() {


    val countDownFlow = flow {


        val start = 5
        var curr = start

        emit(start)
        while (curr > 0) {
            delay(1000L)
            curr--
            emit(curr)
        }
    }
}