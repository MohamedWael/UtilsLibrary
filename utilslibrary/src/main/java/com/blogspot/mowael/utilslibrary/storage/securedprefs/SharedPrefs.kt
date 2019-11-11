package com.blogspot.mowael.utilslibrary.storage.securedprefs

import android.content.Context

val sharedPrefrencesName = "prefs"

class SharedPrefs(context: Context) : AbstractSharedPrefs {

    private val prefs by lazy { context.getSharedPreferences(if (sharedPrefrencesName.isEmpty()) context.packageName else sharedPrefrencesName, Context.MODE_PRIVATE) }

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