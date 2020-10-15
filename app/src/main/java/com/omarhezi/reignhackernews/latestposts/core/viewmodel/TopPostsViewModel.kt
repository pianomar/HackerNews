package com.omarhezi.reignhackernews.latestposts.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.core.repository.LatestPostsRepository
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TopPostsViewModel(
    private val repository: LatestPostsRepository
) : ViewModel() {

    private var _connected = false
    private var _firstLoad: Boolean = false

    val latestPostsRequestResult = liveData {
        val latestPostsResult = repository.getLatestPosts()
        if (latestPostsResult is ResponseResult.Error)
            emit(TopPostsViewState.Error(latestPostsResult.message ?: ""))
    }

    val latestPostsStream = repository.getLatestPostsStream()
        .map {
            TopPostsViewState.Loaded(it)
        }.asLiveData()

    fun setConnected(connected: Boolean) {
        _connected = connected
        if (connected && _firstLoad) {
            viewModelScope.launch { repository.refreshLatestPosts() }
        }
    }

    fun setFirstLoad(firstLoad: Boolean) {
        _firstLoad = firstLoad
    }

    sealed class TopPostsViewState {
        object WaitingForUserAction : TopPostsViewState()
        class Error(val message: String) : TopPostsViewState()
        class Loaded(val posts: List<Post>) : TopPostsViewState()
    }
}
