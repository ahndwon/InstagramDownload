package xyz.thingapps.instagramdownloader

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import okio.Okio
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


object ImageUtil {

    fun saveImage(
        applicationContext: Context,
        bitmap: Bitmap,
        directoryName: String,
        title: String
    ): String {
        val fileName = "$title.jpg"

        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val externalStorageDirectory = Environment.getExternalStorageDirectory().toString()
        val directoryPath = "$externalStorageDirectory${File.separator}$directoryName"
        File(directoryPath).mkdir()
        val path =
            directoryPath + File.separator + fileName

        val file = File(path)

        scan(applicationContext, path)

        val bufferedSink = Okio.buffer(Okio.sink(file))
        bufferedSink.write(bytes.toByteArray())
        bufferedSink.close()

//        file.createNewFile()
//        val fileOutputStream = FileOutputStream(file)
//
//        fileOutputStream.use {
//            it.write(bytes.toByteArray())
//        }

        return file.canonicalPath
    }


    private fun scan(context: Context, path: String) {
//        context.sendBroadcast(
//            Intent(
//                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                Uri.parse(
//                    "file://"
//                            + Environment.getExternalStorageDirectory()
//                )
//            )
//        )
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