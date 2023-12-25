package com.zigcon.common.artifacts.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Long.getDateFromMilliseconds(requiredFormat: String): String {
    val simpleDateFormat = SimpleDateFormat(requiredFormat, Locale.getDefault())
    return simpleDateFormat.format(Date(this))
}

/**
 *
 * @milliSecs : Milliseconds of the date
 * @return the start day milliseconds of input milliseconds(Date)
 */
fun getStartDayMilliSeconds(milliSecs : Long): Long {
    val c = Calendar.getInstance()
    c.timeInMillis = milliSecs
    c[Calendar.HOUR_OF_DAY] = 0
    c[Calendar.MINUTE] = 0
    c[Calendar.SECOND] = 0
    c[Calendar.MILLISECOND] = 0
    return c.timeInMillis
}