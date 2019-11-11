package com.blogspot.mowael.utilslibrary.storage.securedprefs

import android.content.SharedPreferences


internal fun SharedPreferences.putString(key: String, value: String?) {
    edit().putString(key, value).apply()
}

internal fun SharedPreferences.putLong(key: String, value: Long) {
    edit().putLong(key, value).apply()
}

internal fun SharedPreferences.putFloat(key: String, value: Float) {
    edit().putFloat(key, value).apply()
}

internal fun SharedPreferences.putInt(key: String, value: Int) {
    edit().putInt(key, value).apply()
}

internal fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    edit().putBoolean(key, value).apply()
}