package com.zynksoftware.base.developeroptions.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.zynksoftware.base.BuildConfig
import java.io.File
import java.util.ArrayList

object DeveloperUtils {
    const val DEFAULT_DIR_NAME = "logfiles"

    fun deleteFilesBeforeStartingLogs(context: Context) {
        val dirName = DEFAULT_DIR_NAME
        val dirPath = context.filesDir.absolutePath + File.separator.toString() + dirName
        val projDir = File(dirPath)
        val files = projDir.listFiles()
        if (files != null) {
            for (child in files) {
                child.delete()
            }
        }
    }

    fun getUrisOfLogFiles(context: Context): ArrayList<Uri> {
        val uris: ArrayList<Uri> = ArrayList<Uri>()
        val dirPath =
            context.filesDir.absolutePath + File.separator.toString() + DEFAULT_DIR_NAME
        val projDir = File(dirPath)
        val files = projDir.listFiles()
        if (files != null) {
            for (child in files) {
                val file = File(projDir, child.name)
                uris.add(
                    FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + ".fileprovider", file
                    )
                )
            }
        }
        return uris
    }
}