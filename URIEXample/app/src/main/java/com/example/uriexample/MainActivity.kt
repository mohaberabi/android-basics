package com.example.uriexample

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.uriexample.ui.theme.URIExampleTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        val uri = Uri.parse("android.resource://${packageName}/drawable/image")
//
//        val file = File(filesDir, "image.jpg")
//
//
//        val imageBytes = contentResolver.openInputStream(uri)?.use {
//
//            it.readBytes()
//        }
//        FileOutputStream(file).use {
//            it.write(imageBytes)
//        }
//
//        println(imageBytes?.size)
        setContent {
            URIExampleTheme {


                val pickImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { contentUri ->
                        println(contentUri)
                    })



                Button(onClick = {
                    pickImage.launch("image/*")
                }) {

                    Text(text = "pick image")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    URIExampleTheme {
        Greeting("Android")
    }
}