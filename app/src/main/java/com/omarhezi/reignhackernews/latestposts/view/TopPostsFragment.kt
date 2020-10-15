package com.omarhezi.reignhackernews.latestposts.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omarhezi.reignhackernews.R
import com.omarhezi.reignhackernews.latestposts.ConnectivityHelper

class TopPostsFragment : Fragment(), ConnectionListener {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var connectivityHelper: ConnectivityHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_posts, container, false)
    }

    override fun onResume() {
        connectivityManager.registerNetworkCallback(
            ConnectivityHelper.buildNetworkRequest(),
            ConnectivityHelper(this)
        )
        super.onResume()
    }

    override fun onPause() {
        connectivityManager.unregisterNetworkCallback(connectivityHelper)
        super.onPause()
    }

    override fun onConnected() {
        TODO("Not yet implemented")
    }

    override fun onDisconnected() {
        TODO("Not yet implemented")
    }
}