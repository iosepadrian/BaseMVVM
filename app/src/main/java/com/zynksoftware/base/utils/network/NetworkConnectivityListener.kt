package com.zynksoftware.base.utils.network

interface NetworkConnectivityListener {
    fun onConnected()

    fun onDisconnected()
}