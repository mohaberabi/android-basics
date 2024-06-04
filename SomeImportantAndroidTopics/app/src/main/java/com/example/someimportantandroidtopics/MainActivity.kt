package com.example.someimportantandroidtopics

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import com.example.someimportantandroidtopics.services.RunningTrackerServices
import com.example.someimportantandroidtopics.ui.theme.SomeImportantAndroidTopicsTheme
import com.example.someimportantandroidtopics.worker_example.CompressImageWorker

class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager

    //    private val airplaneReciever = AirplaneModeReciever()
    private val viewModel: ImageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        workManager = WorkManager.getInstance(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0

            )
        }

//        registerReceiver(
//            airplaneReciever,
//            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        )
        setContent {
            val workerResult = viewModel.workId?.let { id ->
                workManager.getWorkInfoByIdLiveData(id).observeAsState().value


            }


            LaunchedEffect(workerResult?.outputData) {
                if (workerResult?.outputData != null) {
                    val filePath =
                        workerResult.outputData.getString(CompressImageWorker.KEY_RESULT_PATH)
                    filePath?.let {
                        val bitmap = BitmapFactory.decodeFile(it)
                        viewModel.updateCompressedBitmap(bitmap)
                    }
                }
            }
            SomeImportantAndroidTopicsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        viewModel.unCompressed?.let {
                            Text(text = "Un compressed")

                            AsyncImage(model = it, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        viewModel.compressedBitmap?.let {
                            Text(text = "compressed")

                            Image(bitmap = it.asImageBitmap(), contentDescription = null)
                        }
                    }
//
//                    Column {
//                        Button(onClick = {
//                            Intent(applicationContext, RunningTrackerServices::class.java).also {
//                                it.action = RunningTrackerServices.Actions.START.toString()
//                                startService(it)
//                            }
//                        }) {
//
//                            Text(text = "Start running services")
//                        }
//
//                        Button(onClick = {
//                            Intent(applicationContext, RunningTrackerServices::class.java).also {
//                                it.action = RunningTrackerServices.Actions.STOP.toString()
//                                startService(it)
//                            }
//                        }) {
//
//                            Text(text = "Stop running services")
//                        }
//                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        } ?: return


        viewModel.updateUnCompressed(uri)
        val request = OneTimeWorkRequestBuilder<CompressImageWorker>()
            .setInputData(
                workDataOf(
                    CompressImageWorker.KEY_CONTENT_URI to uri.toString(),
                    CompressImageWorker.KEY_COMPRESSION_THRESHOLD to 1024 * 20L
                )
            ).setConstraints(
                Constraints(
                    requiresStorageNotLow = false,
                )
            ).build()

        viewModel.updateWorkId(request.id)
        workManager.enqueue(request)

    }


}

