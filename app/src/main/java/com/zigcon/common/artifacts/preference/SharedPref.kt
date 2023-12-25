package com.zigcon.common.artifacts.preference

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SharedPref {

    fun getSharedPreference(context: Context,preferenceName: String): SharedPreferences {
        val masterKey = getMasterKey(context)
        return EncryptedSharedPreferences.create(
            context,
            preferenceName,
            masterKey!!,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun getMasterKey(context: Context): MasterKey? {
        try {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val spec = KeyGenParameterSpec.Builder(
                    MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build()

                MasterKey.Builder(context)
                    .setKeyGenParameterSpec(spec)
                    .build()
            } else {
                MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}