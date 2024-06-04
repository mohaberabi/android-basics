package com.example.someimportantandroidtopics

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil.compose.AsyncImage

@Composable
fun ExplainUsingTasksAndIntnets(imageUri: Uri?) {
    Column {

        imageUri?.let {
            AsyncImage(model = it, contentDescription = null)
        }



        Button(onClick = {


//            Intent(Intent.ACTION_MAIN).also {
//                it.`package` = "com.google.android.youtube"
//
//                try {
//                    startActivity(it)
//
//                } catch (e: ActivityNotFoundException) {
//                    e.printStackTrace()
//                }
//            }
        }
        ) {

            Text(text = "Open Youtube")
        }
        Button(
            onClick = {
//                Intent(Intent.ACTION_SEND).apply {
//                    type = "text/plain"
//                    putExtra(Intent.EXTRA_EMAIL, arrayOf("mohab@yahoo.com"))
//                    putExtra(Intent.EXTRA_SUBJECT, "Subject subject")
//                    putExtra(
//                        Intent.EXTRA_TEXT,
//                        "Hi mohab do you think you will be a programmer ? "
//                    )
//
//                }.also {
//                    startActivity(it)
//                }
            },
        ) {

            Text(text = "Share")
        }
    }

}