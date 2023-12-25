package com.zigcon.common.artifacts.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File

fun copyFile(
    sourceFile: File,
    destFile: File,
    overwrite: Boolean = false,
    onCopied: (Boolean) -> Unit = { _ -> }
) {
    sourceFile.copyTo(destFile, overwrite)
    onCopied(destFile.exists())
}

fun Context.openFile(providerUri: String,file: File) {
    val uri = getFileProviderUri(providerUri,file.absolutePath)
    uri?.let {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val mediaType = getMimeType(file.absolutePath)
        intent.setDataAndType(it, mediaType)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Context.getFileProviderUri(providerUri : String,filePath: String?): Uri? {
    return try {
        filePath?.let {
            FileProvider.getUriForFile(
                this,
                providerUri,
                File(filePath)
            )
        }
    } catch (e: Exception) {
        null
    }
}

fun getMimeType(url: String?): String? {
    var type: String? = null
    val extension = MimeTypeMap.getFileExtensionFromUrl(url)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}

fun String?.deleteFile(): Boolean {
    return try {
        when {
            (this.isNullOrEmpty()) -> false
            else -> return (File(this).delete())
        }
    } catch (e: Exception) {
        false
    }
}


