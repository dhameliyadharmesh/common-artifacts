package com.zigcon.common.artifacts.livedata

/**
 * @Author Dharmesh
 * @Date 30-08-2022
 *
 * Information
 **/
interface EventHandler<V> {
    fun onEventUnHandled(obj: V?);
}