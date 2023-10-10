package com.zynksoftware.base.common.caching

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class SessionFileManager @Inject constructor (@ApplicationContext private val context: Context) {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    companion object {
        private const val BUFFER_SIZE = 4096
    }

    fun encryptFile(destination: String, fileInputStream: InputStream, bufferSize: Int = BUFFER_SIZE) {
        val encryptedFile = EncryptedFile.Builder(
            File(destination),
            context,
            mainKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encryptedFile.openFileOutput().apply {
            val bytesIn = ByteArray(bufferSize)
            var read: Int
            while (fileInputStream.read(bytesIn).also { read = it } != -1) {
                write(bytesIn, 0, read)
            }
            flush()
            close()
        }
    }


    fun decryptFile(fileToRead: File): ByteArrayInputStream {
        val encryptedFile = EncryptedFile.Builder(
            fileToRead,
            context,
            mainKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = encryptedFile.openFileInput()
        val byteArrayOutputStream = ByteArrayOutputStream()
        var nextByte: Int = inputStream.read()
        while (nextByte != -1) {
            byteArrayOutputStream.write(nextByte)
            nextByte = inputStream.read()
        }

        return byteArrayOutputStream.toByteArray().inputStream()
    }

}