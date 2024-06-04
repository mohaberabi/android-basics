package com.example.contentproviderexample

import android.Manifest
import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import com.example.contentproviderexample.ui.theme.ContentProviderExampleTheme
import org.jetbrains.annotations.Async
import java.util.Calendar

class MainActivity : ComponentActivity() {
    val viewModel: ImagesViewModel by viewModels<ImagesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 0)


        val imagesProjection = arrayOf(

            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )


        val millisYesterday = Calendar.getInstance().apply {

            add(Calendar.DAY_OF_YEAR, -100)
        }.timeInMillis
        val imageSelection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        val selectionArgsArray = arrayOf(
            millisYesterday.toString()
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imagesProjection,
            imageSelection,
            selectionArgsArray,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            val images = mutableListOf<MediaImage>()


            while (cursor.moveToFirst()) {

                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id,
                )

                images.add(MediaImage(id, name, uri))

            }
            viewModel.updateImages(images)
        }
        setContent {
            ContentProviderExampleTheme {


                LazyColumn {
                    items(viewModel.images) { img ->

                        Column {

                            AsyncImage(
                                model = img.uri,
                                contentDescription = null
                            )

                            Text(text = img.name)
                        }
                    }
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
    ContentProviderExampleTheme {
        Greeting("Android")
    }
}

data class MediaImage(val id: Long, val name: String, val uri: Uri)