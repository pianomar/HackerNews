package com.omarhezi.reignhackernews.latestposts.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omarhezi.reignhackernews.R
import com.omarhezi.reignhackernews.latestposts.ConnectivityHelper
import com.omarhezi.reignhackernews.latestposts.core.viewmodel.TopPostsViewModel
import com.omarhezi.reignhackernews.latestposts.view.adapter.PostSelectionListener
import com.omarhezi.reignhackernews.latestposts.view.adapter.TopPostsAdapter
import com.omarhezi.reignhackernews.latestposts.view.models.PostViewData
import kotlinx.android.synthetic.main.fragment_top_posts.*
import kotlinx.android.synthetic.main.fragment_top_posts.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopPostsFragment : Fragment(), ConnectionListener {

    private var connectivityManager: ConnectivityManager? = null
    private lateinit var connectivityHelper: ConnectivityHelper
    private lateinit var adapter: TopPostsAdapter
    private var _isConnected = false

    private val viewModel by viewModel<TopPostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityHelper = ConnectivityHelper(this)
        viewModel.refreshPosts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_posts, container, false)
        setupAdapter(view)
        setupSwipeToRefresh(view)
        setupPaging(view)
        return view
    }

    private fun setupPaging(view: View) {
        view.topPostsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && _isConnected) { // is scrolling vertically
                    checkForLoadMore()
                }
            }
        })
    }

    private fun checkForLoadMore() {
        val layoutManager = topPostsList.layoutManager as LinearLayoutManager
        val totalItem = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
        if (lastVisibleItem == totalItem - 1) {
            pagingLoading.visibility = View.VISIBLE
            viewModel.getLatestPosts()
        }
    }

    private fun setupSwipeToRefresh(view: View) {
        view.topPostsSwipeRefresh.setOnRefreshListener {
            viewModel.refreshPosts()
        }
    }

    private fun setupAdapter(view: View) {
        adapter = TopPostsAdapter(object : PostSelectionListener {
            override fun onPostSelected(post: PostViewData) {
                post.storyUrl?.let {
                    if (_isConnected) showWebView(it)
                    else showErrorMessage(getString(R.string.offline_error))
                }
            }
        })
        ItemTouchHelper(postItemTouchHelper).attachToRecyclerView(view.topPostsList)
        view.topPostsList.adapter = adapter
    }

    private fun showWebView(url: String) {
        val fragment = WebViewFragment.create(url)
        fragment.show(parentFragmentManager, "WebView_TAG")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            if (it is TopPostsViewModel.TopPostsViewState.Error) {
                topPostsSwipeRefresh.isRefreshing = false
                pagingLoading.visibility = View.GONE
                showErrorMessage("Error ${it.message}")
            }
        })

        viewModel.latestPostsStream.observe(viewLifecycleOwner, Observer {
            topPostsSwipeRefresh.isRefreshing = false
            pagingLoading.visibility = View.GONE
            adapter.submitList(it.posts)
        })
    }

    override fun onResume() {
        connectivityManager?.registerNetworkCallback(
            ConnectivityHelper.buildNetworkRequest(),
            connectivityHelper
        )
        super.onResume()
    }

    override fun onPause() {
        connectivityManager?.unregisterNetworkCallback(connectivityHelper)
        super.onPause()
    }

    override fun onConnectionChanged(connected: Boolean) {
        _isConnected = connected
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private val postItemTouchHelper =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deletePost(viewHolder.adapterPosition)
            }
        }
}