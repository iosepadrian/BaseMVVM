package com.zynksoftware.base.common.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.IOException
import java.security.*
import javax.crypto.*
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Singleton

@Singleton
class DbCrypto {
    companion object {
        private const val ALIAS: String = "STORO_DB_KEY"

        private const val ONE_BYTE_IN_BITS = 8
        private const val ONE_KILOBYTE_IN_BYTES = 1024

        private const val GCM_TAG_LENGTH_IN_BITS = 16
        private const val GCM_TAG_LENGTH_IN_BYTES = GCM_TAG_LENGTH_IN_BITS * ONE_BYTE_IN_BITS
        private const val CHUNK_LENGTH = 32 * ONE_KILOBYTE_IN_BYTES

        private const val KEY_SIZE = 256
    }
    private val blockMode = KeyProperties.BLOCK_MODE_GCM
    private val encryptionPadding = KeyProperties.ENCRYPTION_PADDING_NONE
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")

    init {
        keyStore.load(null)
        createKeystoreAliasIfNeeded()
    }

    private fun createKeystoreAliasIfNeeded() {
        if (!keyStore.containsAlias(ALIAS)) this.createNewKey()
    }

    @Throws(UnrecoverableKeyException::class, NoSuchAlgorithmException::class, KeyStoreException::class, InvalidKeyException::class, IOException::class)
    fun encrypt(plainByte: ByteArray): EncryptResult {
        createKeystoreAliasIfNeeded()
        val encryptKey = getEncryptKey()
        val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + blockMode + "/" + encryptionPadding)
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey)

        val cipherText = ByteArray(plainByte.size + GCM_TAG_LENGTH_IN_BITS)
        System.arraycopy(plainByte, 0, cipherText, 0, plainByte.size)

        var bytesRemainingToRead: Int = plainByte.size
        var inputOffset = 0
        var outputOffset = 0

        while (bytesRemainingToRead > CHUNK_LENGTH) {
            val written = cipher.update(plainByte, inputOffset, CHUNK_LENGTH, cipherText, outputOffset)
            inputOffset += CHUNK_LENGTH
            outputOffset += written
            bytesRemainingToRead -= CHUNK_LENGTH
        }

        cipher.doFinal(plainByte, inputOffset, bytesRemainingToRead, cipherText, outputOffset)
        return EncryptResult(cipherText, cipher.iv)
    }

    @Throws(UnrecoverableKeyException::class, NoSuchAlgorithmException::class, KeyStoreException::class, InvalidAlgorithmParameterException::class, InvalidKeyException::class, IOException::class)
    fun decrypt(encryptedByte: ByteArray, cipherIV: ByteArray): DecryptResult {
        createKeystoreAliasIfNeeded()
        val decryptKey = getDecryptKey()
        val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + blockMode + "/" + encryptionPadding)
        cipher.init(Cipher.DECRYPT_MODE, decryptKey, GCMParameterSpec(GCM_TAG_LENGTH_IN_BYTES, cipherIV))

        val decryptedText = ByteArray(encryptedByte.size - GCM_TAG_LENGTH_IN_BITS)

        var bytesRemainingToRead: Int = encryptedByte.size
        var inputOffset = 0
        var outputOffset = 0

        while (bytesRemainingToRead > CHUNK_LENGTH) {
            val written = cipher.update(encryptedByte, inputOffset, CHUNK_LENGTH, decryptedText, outputOffset)
            inputOffset += CHUNK_LENGTH
            outputOffset += written
            bytesRemainingToRead -= CHUNK_LENGTH
        }

        cipher.doFinal(encryptedByte, inputOffset, bytesRemainingToRead, decryptedText, outputOffset)
        return DecryptResult(decryptedText, cipher.iv)
    }

    @Throws(UnrecoverableKeyException::class, NoSuchAlgorithmException::class, KeyStoreException::class, InvalidKeyException::class, IOException::class)
    fun getEncryptKey(): Key {
        return keyStore.getKey(ALIAS, null) as SecretKey
    }

    @Throws(UnrecoverableKeyException::class, NoSuchAlgorithmException::class, KeyStoreException::class, InvalidAlgorithmParameterException::class, InvalidKeyException::class, IOException::class)
    fun getDecryptKey(): Key {
        return keyStore.getKey(ALIAS, null) as SecretKey
    }

    @Throws(NoSuchProviderException::class, NoSuchAlgorithmException::class, InvalidAlgorithmParameterException::class)
    fun createNewKey() {
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        generator.init(
            KeyGenParameterSpec.Builder(ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(blockMode)
                .setEncryptionPaddings(encryptionPadding)
                .setKeySize(KEY_SIZE)
                .build()
        )
        generator.generateKey()
    }
}
