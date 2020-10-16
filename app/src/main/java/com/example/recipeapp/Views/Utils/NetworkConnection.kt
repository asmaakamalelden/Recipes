package com.example.recipeapp.Views.Utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData


class NetworkConnection(private val context: Context):LiveData<Boolean>() {
    private var connectivityManager: ConnectivityManager=
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateConnection()
        when{
            Build.VERSION.SDK_INT >=Build.VERSION_CODES.N ->{
                connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
            }
            Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP->{
                lollipopNetworkReuest()
            }else->{
            context.registerReceiver(
                networkReciver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkReuest(){
        val requestBuilder=NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),connectivityManagerCallback()
        )
    }

    private fun connectivityManagerCallback():ConnectivityManager.NetworkCallback{
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            networkCallback=object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }

        }
        return networkCallback
    }
    private val networkReciver=object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            updateConnection()
        }

    }
    private fun updateConnection(){
        val activeNetwork:NetworkInfo?=connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected==true)
    }
}