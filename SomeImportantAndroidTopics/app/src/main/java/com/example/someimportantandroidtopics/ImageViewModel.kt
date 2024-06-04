package com.example.someimportantandroidtopics

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.UUID

class ImageViewModel : ViewModel() {


    var unCompressed: Uri? by mutableStateOf(null)
        private set

    var compressedBitmap: Bitmap? by mutableStateOf(null)


    var workId: UUID? by mutableStateOf(null)
        private set


    fun updateUnCompressed(uri: Uri?) {
        unCompressed = uri
    }

    fun updateCompressedBitmap(bitmap: Bitmap?) {
        compressedBitmap = bitmap
    }


    fun updateWorkId(uuid: UUID?) {
        workId = uuid
    }
//    var imageUri: Uri? by mutableStateOf(null)
//
//
//    fun setUri(img: Uri?) {
//        imageUri = img
//    }
}