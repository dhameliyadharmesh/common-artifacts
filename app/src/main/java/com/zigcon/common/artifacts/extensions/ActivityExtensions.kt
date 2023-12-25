package com.zigcon.common.artifacts.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zigcon.common.artifacts.base.BaseViewModelFactory

/**
 * @Author Dharmesh
 * @Date 24-10-2022
 *
 * Information
 **/

inline fun <reified T : Activity> Activity.startActivityInlineWithFinishAll(bundle: Bundle? = null) {
    if (bundle != null) {
        startActivity(
            Intent(this.applicationContext, T::class.java).putExtras(bundle)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    } else {
        startActivity(
            Intent(
                this.applicationContext, T::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )

    }
    this.finishAffinity()
}

inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle? = null) {
    if (bundle != null) {
        startActivity(
            Intent(this.applicationContext, T::class.java).putExtras(bundle)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    } else {
        startActivity(
            Intent(
                this.applicationContext, T::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )

    }
    this.finishAffinity()
}

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

fun Activity.shareApp(applicationId : String,appName : String) {
    val shareBodyText = "https://play.google.com/store/apps/details?id=$applicationId"
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, appName)
        putExtra(Intent.EXTRA_TEXT, shareBodyText)
    }
    startActivity(Intent.createChooser(sendIntent, null))
}