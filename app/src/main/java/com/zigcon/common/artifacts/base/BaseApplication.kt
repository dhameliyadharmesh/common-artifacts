package com.zigcon.common.artifacts.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.common.managers.NetworkManager

/**
 * @Author Dharmesh
 * @Date 30-08-2022
 *
 * Information
 **/

open class BaseApplication : Application() {

    lateinit var networkManager: NetworkManager

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        networkManager =  NetworkManager(this)
    }
}