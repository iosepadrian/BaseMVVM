package com.zynksoftware.base.common.extensions

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.zynksoftware.base.utils.file.FileUtils
import java.io.File
import java.io.InputStream

fun Uri.toActualFile(context: Context): File? {
    val inputStream = context.contentResolver.openInputStream(this)
    val fileType =
        MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(this))
    return if (inputStream != null) {

        val imageFile = File.createTempFile(FileUtils.getImageFileName(), ".${fileType}")
        imageFile.copyInputStreamToFile(inputStream)
        imageFile
    } else {
        null
    }
}

fun File.copyInputStreamToFile(inputStream: InputStream) {
    this.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
}