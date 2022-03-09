package com.zynksoftware.base.developeroptions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.zynksoftware.base.R
import java.io.File
import java.io.InterruptedIOException
import java.util.concurrent.atomic.AtomicBoolean

class LogProvider(private val context: Context) : Runnable{


    private var worker: Thread? = null
    private val running = AtomicBoolean(false)
    private val stopped = AtomicBoolean(true)
    private val defaultFileName = context.getString(R.string.baseLogFileName)
    private val p=Runtime.getRuntime().exec("logcat")

    /*fun ControlSubThread(sleepInterval: Int) {
        interval = sleepInterval
    }*/

    fun start() {
        worker = Thread(this)
        running.set(true)
        worker!!.start()
    }

    /*fun stop() {
        running.set(false)
    }*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun interrupt() {
        running.set(false)
        worker!!.interrupt()
    }

    private fun isRunning(): Boolean {
        return running.get()
    }

    /*fun isStopped(): Boolean {
        return stopped.get()
    }*/

    override fun run() {
        stopped.set(false)
        while (running.get()) {
            try {
                val dirPath = context.filesDir.absolutePath + File.separator.toString() + context.getString(
                    R.string.baseLogDirName)
                val projDir = File(dirPath)
                if (!projDir.exists()) projDir.mkdirs()
                var linecount = 0
                p
                    .inputStream
                    .bufferedReader()
                    .useLines { lines ->
                            lines.forEach { line ->
                                if (isRunning()) {
                                val fileName =
                                    defaultFileName + (linecount / 500).toString() + ".txt"
                                val file = File(projDir, fileName)
                                file.appendText(line + "\n")
                                linecount++
                            }
                        }
                    }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            } catch (e:InterruptedIOException){
                Thread.currentThread().interrupt()
            }
            // do something
        }
        stopped.set(true)
    }



}