package com.example.kotlinflows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kotlinflows.flowoperator.ExplainOperatorFlowScreen
import com.example.kotlinflows.simpleflow.CountDownScreen
import com.example.kotlinflows.ui.theme.KotlinFlowsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowsTheme {


                ExplainOperatorFlowScreen()
//                CountDownScreen()
            }
        }
    }
}

