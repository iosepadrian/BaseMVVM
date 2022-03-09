package com.zynksoftware.base.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zynksoftware.base.utils.ConsumableLiveData

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observe(liveData: ConsumableLiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { t -> action(t) } })
}