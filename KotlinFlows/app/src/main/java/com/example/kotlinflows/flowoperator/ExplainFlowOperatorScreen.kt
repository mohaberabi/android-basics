package com.example.kotlinflows.flowoperator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable

fun ExplainOperatorFlowScreen(
    viewModel: OperatorsFlowViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

) {


    val counterEvenNosOnly = viewModel.counter

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = " Even numbers only collected  ${counterEvenNosOnly.toString()}")
    }

}