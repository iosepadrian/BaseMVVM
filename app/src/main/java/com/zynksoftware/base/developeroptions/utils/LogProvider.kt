package com.zynksoftware.base.developeroptions.utils

import android.content.Context
import java.io.File
import java.io.InterruptedIOException
import java.util.concurrent.atomic.AtomicBoolean

class LogProvider(private val context: Context) : Runnable {

    private var worker: Thread? = null
    private val running = AtomicBoolean(false)
    private val stopped = AtomicBoolean(true)
    private val p = Runtime.getRuntime().exec("logcat")

    companion object {
        private const val DEFAULT_FILE_NAME = "logfile"
        private const val MAX_LINES_PER_FILE = 2000
    }

    fun start() {
        worker = Thread(this)
        running.set(true)
        worker!!.start()
    }

    fun interrupt() {
        running.set(false)
        worker!!.interrupt()
    }

    private fun isRunning(): Boolean {
        return running.get()
    }

    override fun run() {
        stopped.set(false)
        while (running.get()) {
            try {
                val dirPath =
                    context.filesDir.absolutePath + File.separator.toString() + DeveloperUtils.DEFAULT_DIR_NAME
                val projDir = File(dirPath)
                if (!projDir.exists()) {
                    projDir.mkdirs()
                }
                var linecount = 0
                p
                    .inputStream
                    .bufferedReader()
                    .useLines { lines ->
                        lines.forEach { line ->
                            if (isRunning()) {
                                val fileName =
                                    DEFAULT_FILE_NAME + (linecount / MAX_LINES_PER_FILE).toString() + ".txt"
                                val file = File(projDir, fileName)
                                file.appendText(line + "\n")
                                linecount++
                            }
                        }
                    }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            } catch (e: InterruptedIOException) {
                Thread.currentThread().interrupt()
            }
        }
        stopped.set(true)
    }
}