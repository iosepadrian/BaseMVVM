package com.zynksoftware.base.utils.file

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    fun getTmpFileUri(context: Context): Uri {
        val tmpFile =
            File.createTempFile(UUID.randomUUID().toString(), ".png", context.cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", tmpFile)
    }

    fun getImageFileName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "IMG_" + timeStamp + "_"
    }

    fun getRawUri(context: Context, resId: Int): Uri? {
        return Uri.parse("android.resource://${context.packageName}/${resId}")
    }
}