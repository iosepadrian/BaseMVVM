package com.zynksoftware.base.developeroptions

import android.content.Context
import java.io.File

object Utils {

    fun deleteFilesBeforeStartingLogs(context: Context) {
        val dirName = "logfiles"
        val dirPath = context.filesDir.absolutePath + File.separator.toString() + dirName
        val projDir = File(dirPath)
        if (projDir.listFiles() != null) {
            for (child in projDir.listFiles()) {
                child.delete()
            }
        }
    }
}