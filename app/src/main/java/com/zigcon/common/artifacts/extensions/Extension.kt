package com.zigcon.common.artifacts.extensions

//import androidx.navigation.NavDirections
//import androidx.navigation.findNavController
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.annotation.AnyRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import java.util.concurrent.TimeUnit


////////////////////////////////////////

// Font - Regular
fun Context.regularFont(): Typeface {
    return Typeface.createFromAsset(this.assets, "fonts/Roboto-Regular.ttf")
}

// Font - Bold
fun Context.boldFont(): Typeface {
    return Typeface.createFromAsset(this.assets, "fonts/Roboto-Bold.ttf")
}

// Font - SemiBold
fun Context.semiBoldFont(): Typeface {
    return Typeface.createFromAsset(this.assets, "fonts/Roboto-Bold.ttf")
}

// Font - Medium
fun Context.mediumFont(): Typeface {
    return Typeface.createFromAsset(this.assets, "fonts/Roboto-Medium.ttf")
}

fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun getResourceUri(@AnyRes resourceId: Int): String =
    Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority("com.app.batteryapp")
        .path(resourceId.toString()).build().toString()

fun String?.safeString(defaultData: String = ""): String {
    return this ?: defaultData
}

fun Context?.shortToast(resourceId: Int? = null) {
    if (resourceId != null) {
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.shortToast(resourceId: String? = null) {
    if (resourceId != null) {
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.longToast(resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_LONG).show()
}

fun Uri.name(context: Context): String {
    when (scheme) {
        ContentResolver.SCHEME_FILE -> {
            return toFile().nameWithoutExtension
        }
        ContentResolver.SCHEME_CONTENT -> {
            val cursor = context.contentResolver.query(
                this, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null
            ) ?: throw Exception("Failed to obtain cursor from the content resolver")
            cursor.moveToFirst()
            if (cursor.count == 0) {
                throw Exception("The given Uri doesn't represent any file")
            }
            val displayNameColumnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val displayName = cursor.getString(displayNameColumnIndex)
            cursor.close()
            return displayName.substringBeforeLast(".")
        }
        ContentResolver.SCHEME_ANDROID_RESOURCE -> {
            // for uris like [android.resource://com.example.app/1234567890]
            var resourceId = lastPathSegment?.toIntOrNull()
            if (resourceId != null) {
                return context.resources.getResourceName(resourceId)
            }
            // for uris like [android.resource://com.example.app/raw/sample]
            val packageName = authority
            val resourceType = if (pathSegments.size >= 1) {
                pathSegments[0]
            } else {
                throw Exception("Resource type could not be found")
            }
            val resourceEntryName = if (pathSegments.size >= 2) {
                pathSegments[1]
            } else {
                throw Exception("Resource entry name could not be found")
            }
            resourceId = context.resources.getIdentifier(
                resourceEntryName, resourceType, packageName
            )
            return context.resources.getResourceName(resourceId)
        }
        else -> {
            // probably a http uri
            return toString().substringBeforeLast(".").substringAfterLast("/")
        }
    }
}

fun Uri?.getDuration(context: Context): Int {
    if (this == null) return 0
    val mediaMetadataRetriever = MediaMetadataRetriever()
    mediaMetadataRetriever.setDataSource(context, this)
    val durationStr =
        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
    try {
        val milli = durationStr?.toLong()
        mediaMetadataRetriever.release()
        return TimeUnit.MILLISECONDS.toSeconds(milli!!).toInt()
    } catch (e: Exception) {
        return 0
    }
}


//fun openFragment(view: View, directions: NavDirections) {
//    view.findNavController().navigate(directions)
//}

/**
 * method will use for gone view
 */
fun View?.beGone() {
    if (this != null && this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

/**
 * method will use for visible view
 */
fun View?.beVisible() {
    if (this != null && this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

/**
 * method will use for invisible view
 */
fun View?.beInVisible() {
    if (this != null && this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
}


fun String?.stringSafety(str: String? = ""): String? {
    return this ?: str
}

fun String?.stringSafetyFire(str: String = ""): String {
    if(this == null){
        return str
    }else{
        return this
    }
}

fun Int?.intSafety(str: Int = 0): Int {
    return this ?: str
}

@SuppressLint("NewApi")
fun Uri?.getRealPathFromURI_API19(context: Context): String? {
    return this?.let {
        var filePath: String? = null
        val wholeID = DocumentsContract.getDocumentId(this)

        // Split at colon, use second item in the array
        val id = wholeID.split(":".toRegex()).toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )
        val columnIndex = cursor!!.getColumnIndex(column[0])
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }
        cursor.close()
        filePath
    } ?: run {
        null
    }
}

fun View.focus(){
    this.requestFocus()
}

/**
 * method will use for get color using id
 * @param color - color id
 */
fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)
