package com.example.someimportantandroidtopics.worker_example

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt

class CompressImageWorker(
    private val context: Context,
    private val params: WorkerParameters,
) : CoroutineWorker(
    context,
    params
) {

    companion object {
        const val KEY_CONTENT_URI: String = "KEY_CONTENT_URI"
        const val KEY_COMPRESSION_THRESHOLD: String = "KEY_COMPRESSION_THRESHOLD"
        const val KEY_RESULT_PATH: String = "KEY_RESULT_PATH"

    }

    override suspend fun doWork(): Result {

        return withContext(Dispatchers.IO) {

            val stringUri = params.inputData.getString(KEY_CONTENT_URI)

            val compressionTresholdByte = params.inputData.getLong(
                KEY_COMPRESSION_THRESHOLD,
                0L
            )
            val uri = Uri.parse(stringUri)
            val bytes = context.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: return@withContext Result.failure()

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            var outBytes: ByteArray
            var quality = 100

            do {
                val outPutStream = ByteArrayOutputStream()
                outPutStream.use { oS ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outPutStream)
                    outBytes = oS.toByteArray()

                    quality -= (quality * 0.1).roundToInt()

                }
            } while (outBytes.size > compressionTresholdByte && quality > 5)
            val file = File(context.cacheDir, "${params.id}.jpg")
            file.writeBytes(outBytes)
            return@withContext Result.success(workDataOf(KEY_RESULT_PATH to file.absolutePath))
        }


    }
}