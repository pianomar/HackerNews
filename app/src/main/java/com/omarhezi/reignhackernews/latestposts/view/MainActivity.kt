package com.omarhezi.reignhackernews.latestposts.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omarhezi.reignhackernews.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

interface ConnectionListener {
    fun onConnectionChanged(connected: Boolean)
}