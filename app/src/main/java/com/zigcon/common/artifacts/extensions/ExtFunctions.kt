package com.zigcon.common.artifacts.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.zigcon.common.artifacts.R
import com.zigcon.common.artifacts.utils.UIUtils

fun Context.openAppPlayStorePage(packageName: String) {
    try {
        val appStoreIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        startActivity(appStoreIntent)
    } catch (exception: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

fun Context.sharePhoto(shareUri: Uri?) {
    try {
        shareUri?.let {
            val intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_STREAM, it)
                this.type = "*/*"
            }
            startActivity(Intent.createChooser(intent, "Share to"))
        }
    } catch (e: Exception) {
        Toast.makeText(this,e.message.stringSafetyFire("Error"),Toast.LENGTH_LONG).show()
    }
}

fun Context.repostPhotoOnWhatsApp(shareUri: Uri?,packageName: String) {
    shareUri?.let {
        val intent = Intent().apply {
            `package` = packageName
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_STREAM, it)
            this.type = "*/*"
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            if(packageName == "com.whatsapp"){
                shortToast(R.string.whatsapp_not_installed)
            } else if(packageName == "com.whatsapp.w4b"){
                shortToast(R.string.wa_business_not_installed)
            }
        }
    }
}

fun PackageManager.whatsappInstalledOrNot(str: String): Boolean {
    return try {
        this.getPackageInfo(str, 1)
        true
    } catch (unused: PackageManager.NameNotFoundException) {
        false
    }
}

fun Activity.shareContent(content : String){
    val intent2 = Intent()
    intent2.action = Intent.ACTION_SEND
    intent2.type = "text/plain"
    intent2.putExtra(Intent.EXTRA_TEXT, content)
    startActivity(Intent.createChooser(intent2, "Share via"))
}

fun Activity.showToast(resId: Int?) {
    resId?.let {
        UIUtils.showToast(this, it)
    }
}

fun Activity.showToast(resId: String?) {
    resId?.let {
        UIUtils.showToast(this, it)
    }
}
