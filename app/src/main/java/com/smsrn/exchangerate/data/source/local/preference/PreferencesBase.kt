package com.smsrn.exchangerate.data.source.local.preference

import android.content.Context
import android.content.SharedPreferences

open class PreferencesBase(context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    protected fun put(key: String, objectValue: Any?) {
        val editor = pref.edit()
        when (objectValue) {
            is String -> editor.putString(key, objectValue)
            is Int -> editor.putInt(key, objectValue)
            is Long -> editor.putLong(key, objectValue)
            is Boolean -> editor.putBoolean(key, objectValue)
            is Float -> editor.putFloat(key, objectValue)
            else -> editor.putString(key, objectValue.toString())
        }
        editor.apply()
    }

    protected operator fun contains(key: String): Boolean {
        return pref.contains(key)
    }

    protected operator fun get(key: String, defaultObject: Any?): Any? {
        return when (defaultObject) {
            is String -> pref.getString(key, defaultObject)
            is Int -> pref.getInt(key, defaultObject)
            is Long -> pref.getLong(key, defaultObject)
            is Boolean -> pref.getBoolean(key, defaultObject)
            is Float -> pref.getFloat(key, defaultObject)
            else -> null
        }
    }

    protected fun remove(key: String) {
        val editor = pref.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        pref.edit().clear().apply()
    }
}