package com.blogspot.mowael.utilslibrary.storage.securedprefs

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys

internal class EncryptedSharedPrefs(context: Context) : AbstractSharedPrefs {

    private val prefs = EncryptedSharedPreferences.create(
            context,
            "mva10_secured_prefs",
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun putString(key: String, value: String?) = prefs.putString(key, value)

    override fun getString(key: String, defaultValue: String?): String? = prefs.getString(key, defaultValue)

    override fun putInt(key: String, value: Int) = prefs.putInt(key, value)

    override fun getInt(key: String, defaultValue: Int): Int? = prefs.getInt(key, defaultValue)

    override fun putLong(key: String, value: Long) = prefs.putLong(key, value)

    override fun getLong(key: String, defaultValue: Long): Long = prefs.getLong(key, defaultValue)

    override fun putFloat(key: String, value: Float) = prefs.putFloat(key, value)

    override fun getFloat(key: String, defaultValue: Float): Float = prefs.getFloat(key, defaultValue)

    override fun putBoolean(key: String, value: Boolean) = prefs.putBoolean(key, value)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = prefs.getBoolean(key, defaultValue)
}