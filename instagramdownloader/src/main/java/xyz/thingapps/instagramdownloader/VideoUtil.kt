package xyz.thingapps.instagramdownloader

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import okhttp3.ResponseBody
import okio.Okio
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


object VideoUtil {

    const val DIRECTORY_NAME = "Regrammer_Video"

    fun saveVideo(
        applicationContext: Context,
        responseBody: ResponseBody,
        directoryName: String,
        title: String
    ): String {
        val fileName = "$title.mp4"

        val externalStorageDirectory = Environment.getExternalStorageDirectory().toString()
        val directoryPath = "$externalStorageDirectory${File.separator}$directoryName"
        File(directoryPath).mkdir()
        val path =
            directoryPath + File.separator + fileName

        val file =
            File(path)

        Log.d("VideoUtil", "path : $path")

        val bufferedSink = Okio.buffer(Okio.sink(file))
        bufferedSink.writeAll(responseBody.source())
        bufferedSink.close()

        scan(applicationContext, path)

        return file.canonicalPath
    }

    fun getVideo(
        responseBody: ResponseBody,
        title: String
    ): File {
        val fileName = "$title.mp4"

        val externalStorageDirectory = Environment.getExternalStorageDirectory().toString()
        val file = File.createTempFile(title, ".mp4")

        val bufferedSink = Okio.buffer(Okio.sink(file))
        bufferedSink.writeAll(responseBody.source())
        bufferedSink.close()

        return file
    }


    private fun scan(context: Context, path: String) {
        context.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse(
                    "file://"
                            + path
                )
            )
        )
    }

    fun Bitmap.convertToFile(): File {
        val bytes = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val file =
            File.createTempFile("temp", ".jpg")

        val fileOutputStream = FileOutputStream(file)

        fileOutputStream.use {
            file.createNewFile()
            it.write(bytes.toByteArray())
        }
        return file
    }
}