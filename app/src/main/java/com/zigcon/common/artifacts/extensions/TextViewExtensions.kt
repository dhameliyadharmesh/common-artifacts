package com.zigcon.common.artifacts.extensions

import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.setTextColor(color: String) {
    this.setTextColor(Color.parseColor(color))
}

fun TextView.setTextBackgroundColor( color: String) {
    this.setBackgroundColor(Color.parseColor(color))
}

fun TextView.resetError() {
    this.error = null
    this.text = null
}

fun TextView.showError(error: String) {
    this.error = error
    this.text = error
}

//Set text view data
fun TextView.setTextData(value: Int) {
    this.text = value.toString()
}

fun TextView.setDrawable(right: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context,right), null)
}

