package com.blogspot.mowael.utilslibrary.storage.securedprefs

import android.content.Context
import android.util.Base64
import com.google.gson.Gson


internal class LegacyEncryptedSharedPrefs(context: Context) : AbstractSharedPrefs {

    private val prefs = SharedPrefs(context)

    override fun putString(key: String, value: String?) {
        val encryptedKey = Base64.encodeToString(key.toByteArray(), Base64.NO_WRAP)
        if (value != null) {
            val encryptedData = EncryptionUtil().encrypt(value.toByteArray(), key.toCharArray())
            val encryptedValue = Base64.encodeToString(Gson().toJson(encryptedData, EncryptedData::class.java).toByteArray(), Base64.NO_WRAP)
            prefs.putString(encryptedKey, encryptedValue)
        } else {
            prefs.putString(encryptedKey, value)
        }
    }

    override fun getString(key: String, defaultValue: String?): String? {
        var value = defaultValue
        val encryptedKey = Base64.encodeToString(key.toByteArray(), Base64.NO_WRAP)
        val encryptedValue = prefs.getString(encryptedKey, defaultValue)
        if (encryptedValue != null) {
            val encryptedData = Gson().fromJson(String(Base64.decode(encryptedValue, Base64.NO_WRAP), Charsets.UTF_8), EncryptedData::class.java)
            EncryptionUtil().decrypt(encryptedData, key.toCharArray())?.let {
                value = String(it, Charsets.UTF_8)
            }
        }
        return value
    }

    override fun putInt(key: String, value: Int) = putString(key, value.toString())

    override fun getInt(key: String, defaultValue: Int): Int? = getString(key, defaultValue.toString())?.toInt()
            ?: defaultValue

    override fun putBoolean(key: String, value: Boolean) = putString(key, value.toString())

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = getString(key, defaultValue.toString())?.toBoolean()
            ?: defaultValue

    override fun putLong(key: String, value: Long) = putString(key, value.toString())

    override fun getLong(key: String, defaultValue: Long): Long = getString(key, defaultValue.toString())?.toLong()
            ?: defaultValue

    override fun putFloat(key: String, value: Float) = putString(key, value.toString())

    override fun getFloat(key: String, defaultValue: Float): Float = getString(key, defaultValue.toString())?.toFloat()
            ?: defaultValue


}