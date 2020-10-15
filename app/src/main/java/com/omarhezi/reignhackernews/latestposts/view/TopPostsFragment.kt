package com.omarhezi.reignhackernews.latestposts.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.omarhezi.reignhackernews.R
import com.omarhezi.reignhackernews.latestposts.ConnectivityHelper
import com.omarhezi.reignhackernews.latestposts.core.viewmodel.TopPostsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopPostsFragment : Fragment(), ConnectionListener {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var connectivityHelper: ConnectivityHelper

    private val viewModel by viewModel<TopPostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        viewModel.setFirstLoad(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.latestPostsRequestResult.observe(viewLifecycleOwner, Observer {
            it as TopPostsViewModel.TopPostsViewState.Error
            Toast.makeText(
                context,
                "Error ${it.message}",
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.latestPostsStream.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                context,
                "Success",
                Toast.LENGTH_SHORT
            ).show()
            Log.i("TAG", "onViewCreated: ${it.posts.joinToString()}")
        })
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
        viewModel.setConnected(true)
    }

    override fun onDisconnected() {
        viewModel.setConnected(false)
    }
}