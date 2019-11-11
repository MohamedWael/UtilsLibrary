package com.blogspot.mowael.utilslibrary.storage.securedprefs

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

private const val SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1"
private const val CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding"
private const val KEY_SPEC_ALGORITHM = "AES"
private const val KEY_LENGTH = 256
private const val ITERATION_COUNT = 1342

class EncryptionUtil {

    fun encrypt(dataToEncrypt: ByteArray, password: CharArray): EncryptedData {

        //Random salt for next step
        val random = SecureRandom()
        val salt = ByteArray(KEY_LENGTH)
        random.nextBytes(salt)

        //PBKDF2 - derive the key from the password, don't use passwords directly
        val pbKeySpec = PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH)
        val secretKeyFactory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, KEY_SPEC_ALGORITHM)

        //Create initialization vector for AES
        val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
        val iv = ByteArray(size = 16)
        ivRandom.nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        //Encrypt
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(dataToEncrypt)

        return EncryptedData(encrypted, iv, salt)
    }

    fun decrypt(data: EncryptedData, password: CharArray): ByteArray? {
        var decrypted: ByteArray? = null
        //regenerate key from password
        val pbKeySpec = PBEKeySpec(password, data.salt, ITERATION_COUNT, KEY_LENGTH)
        val secretKeyFactory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, KEY_SPEC_ALGORITHM)

        //Decrypt
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        val ivSpec = IvParameterSpec(data.iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        decrypted = cipher.doFinal(data.encrypted)
        return decrypted
    }

}

data class EncryptedData(val encrypted: ByteArray, val iv: ByteArray, val salt: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptedData

        if (!encrypted.contentEquals(other.encrypted)) return false
        if (!iv.contentEquals(other.iv)) return false
        if (!salt.contentEquals(other.salt)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = encrypted.contentHashCode()
        result = 31 * result + iv.contentHashCode()
        result = 31 * result + salt.contentHashCode()
        return result
    }
}