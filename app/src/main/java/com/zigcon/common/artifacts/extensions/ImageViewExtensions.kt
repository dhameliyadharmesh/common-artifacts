package com.zigcon.common.artifacts.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

fun ImageView.onGlide(it: Uri) {
    it.let {
        Glide.with(this.context).load(it).into(this)
    }
}

fun ImageView.onGlide(it: String? = null, placeHolderId: Int) {
    it?.let {
        Glide.with(this.context).load(it).into(this)
    } ?: run {
        this.setImageDrawable(ContextCompat.getDrawable(this.context,placeHolderId))
    }
}

fun ImageView.loadLocalGif(gifResId: Int) {
    Glide.with(this.context).asGif().load(gifResId).into(this);
}

fun ImageView.tintVectorColor(@ColorRes color: Int?) {
    if (color == null) {
        this.colorFilter = null
    } else {
        this.setColorFilter(this.context.color(color), android.graphics.PorterDuff.Mode.SRC_IN)
    }
}