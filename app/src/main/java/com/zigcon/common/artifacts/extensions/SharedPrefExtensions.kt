package com.zigcon.common.artifacts.extensions

import android.content.SharedPreferences
import com.google.gson.GsonBuilder

fun <T> SharedPreferences.put(key: String, `object`: T) {
    val editor = this.edit()
    //Convert object to JSON String.
    val jsonString = GsonBuilder().create().toJson(`object`)
    //Save that String in SharedPreferences
    editor.putString(key, jsonString).apply()
}

inline fun <reified T> SharedPreferences.get(key: String): T? {
    //We read JSON String which was saved.
    val value = this.getString(key, null) ?: return null
    //JSON String was found which means object can be read.
    //We convert this JSON String to model object. Parameter "c" (of
    //type Class < T >" is used to cast.
    return GsonBuilder().create().fromJson(value, T::class.java)
}

inline fun <reified T> SharedPreferences.getModel(key: String): T? {
    //We read JSON String which was saved.
    val value = this.getString(key, null)
    return value?.let {
        GsonBuilder().create().fromJson(value, T::class.java)
    } ?: run {
        null
    }
}

fun SharedPreferences.clearPreference(keyString: String) {
    val editor = this.edit()
    editor.remove(keyString)
    editor?.apply()
    editor?.commit()
}

fun SharedPreferences.clear() {
    val editor = this.edit()
    editor.clear()
    editor.apply()
}
