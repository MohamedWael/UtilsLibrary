package com.blogspot.mowael.utilslibrary.storage.securedprefs

import android.content.Context
import android.os.Build

internal interface AbstractSharedPrefs {
    fun putString(key: String, value: String?)
    fun getString(key: String, defaultValue: String?): String?

    fun putInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int): Int?

    fun putLong(key: String, value: Long)
    fun getLong(key: String, defaultValue: Long): Long

    fun putFloat(key: String, value: Float)
    fun getFloat(key: String, defaultValue: Float): Float

    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
}

internal fun createSecuredPrefs(context: Context): AbstractSharedPrefs {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        EncryptedSharedPrefs(context)
    } else {
        LegacyEncryptedSharedPrefs(context)
    }
}