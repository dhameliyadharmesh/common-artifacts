package com.common.managers

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData


/**
 * @Authoer Dharmesh
 * @Date 30-08-2022
 *
 * Information
 **/
@RequiresApi(Build.VERSION_CODES.M)
class NetworkManager(context: Context) {
     var networkData: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        // More here
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        // Connectivity manager to access network service
        val connectivityManager: ConnectivityManager =
            context.getSystemService(ConnectivityManager::class.java)

        // network callback to handle network event turn off or turn on
        val networkCallback: NetworkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkData.postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkData.postValue(false)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                //                final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isConnected(context: Context): Boolean {
        // Connectivity manager to access network service
        val cm: ConnectivityManager? = context.getSystemService(ConnectivityManager::class.java)
        if (cm != null) {
            val nc = cm.getNetworkCapabilities(cm.activeNetwork)
            if (nc != null) {
                return if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    true
                } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    true
                } else nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
        }
        return false
    }
}