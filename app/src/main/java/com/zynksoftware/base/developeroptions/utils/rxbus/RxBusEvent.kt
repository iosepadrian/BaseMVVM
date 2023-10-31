package com.zynksoftware.base.developeroptions.utils.rxbus

sealed class RxBusEvent {
    data class LogOut(val logout: Boolean) : RxBusEvent()
}