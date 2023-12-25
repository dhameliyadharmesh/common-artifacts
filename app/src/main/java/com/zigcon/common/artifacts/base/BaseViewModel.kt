package com.zigcon.common.artifacts.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zigcon.common.artifacts.livedata.EventData

/**
 * @Author Dharmesh
 * @Date 30-08-2022
 *
 * Information
 **/
open class BaseViewModel() : ViewModel() {

    private var toastData: MutableLiveData<EventData<Int>?>? = null
    private var toastStrData: MutableLiveData<EventData<String>?>? = null

    open fun getToastData(): MutableLiveData<EventData<Int>?>? {
        return if (toastData == null) MutableLiveData() else toastData.also { toastData = it }
    }

    open fun getToastStrData(): MutableLiveData<EventData<String>?>? {
        return if (toastStrData == null) MutableLiveData() else toastStrData.also {
            toastStrData = it
        }
    }
}