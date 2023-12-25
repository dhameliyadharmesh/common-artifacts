package com.zigcon.common.artifacts.livedata

import androidx.lifecycle.Observer


/**
 * @Author Dharmesh
 * @Date 30-08-2022
 *
 * Information
 **/
class EventObserver<T>(private var onEventUnhandledContent: EventHandler<T>?)  : Observer<EventData<T>> {

    override fun onChanged(event: EventData<T>) {
        if(event != null){
            onEventUnhandledContent?.onEventUnHandled(event.getContentIfNotHandled());
        }
    }

}