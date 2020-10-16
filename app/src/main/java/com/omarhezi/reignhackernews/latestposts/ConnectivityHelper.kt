package com.omarhezi.reignhackernews.latestposts

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.omarhezi.reignhackernews.latestposts.view.ConnectionListener

class ConnectivityHelper(private val listener: ConnectionListener) :
    ConnectivityManager.NetworkCallback() {

    companion object {
        fun buildNetworkRequest(): NetworkRequest {
            val builder = NetworkRequest.Builder()
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            return builder.build()
        }
    }

    override fun onUnavailable() {
        super.onUnavailable()
        listener.onConnectionChanged(false)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        listener.onConnectionChanged(false)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        listener.onConnectionChanged(true)
    }
}