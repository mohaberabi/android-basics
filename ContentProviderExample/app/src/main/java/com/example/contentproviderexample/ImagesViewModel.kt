package com.example.contentproviderexample

import android.provider.MediaStore.Images.Media
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImagesViewModel : ViewModel() {


    var images by mutableStateOf(emptyList<MediaImage>())
        private set


    fun updateImages(images: List<MediaImage>) {
        this.images = images
    }
}