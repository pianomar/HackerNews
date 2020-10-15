package com.omarhezi.reignhackernews.latestposts.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omarhezi.reignhackernews.R
import com.omarhezi.reignhackernews.latestposts.ConnectivityHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

interface ConnectionListener {
    fun onConnected()
    fun onDisconnected()
}