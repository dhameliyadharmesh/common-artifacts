package com.zigcon.common.artifacts.livedata

/**
 * @Author Dharmesh
 * @Date 30-08-2022
 *
 * Information
 **/
class EventData<T>(content: T?) {

    private var mContent: T? = content
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }
    }

    fun peekContent(): T? {
        return mContent
    }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }
}