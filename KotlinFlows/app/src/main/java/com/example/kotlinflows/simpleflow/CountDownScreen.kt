package com.example.kotlinflows.simpleflow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinflows.simpleflow.AppViewModel


@Composable

fun CountDownScreen(

    viewmodel: AppViewModel = viewModel()
) {


    val time = viewmodel.counter
    val timeAsLatest = viewmodel.counterAsLatest

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Text(text = " Collecting ${time.toString()}")
        Text(text = " Colelction Latest  ${timeAsLatest.toString()}")


    }
}