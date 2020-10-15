package com.omarhezi.reignhackernews.latestposts.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.omarhezi.reignhackernews.latestposts.core.models.Post
import com.omarhezi.reignhackernews.latestposts.core.repository.LatestPostsRepository
import com.omarhezi.reignhackernews.latestposts.misc.FormatUtil
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import com.omarhezi.reignhackernews.latestposts.view.models.PostViewData
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TopPostsViewModel(
    private val repository: LatestPostsRepository,
    private val formatUtil: FormatUtil
) : ViewModel() {

    var connected = false
        private set
    private var _firstLoad: Boolean = false

    val latestPostsRequestResult = liveData {
        val latestPostsResult = repository.getLatestPosts()
        if (latestPostsResult is ResponseResult.Error) {
            emit(TopPostsViewState.Error(latestPostsResult.message ?: ""))
            emit(TopPostsViewState.WaitingForUserAction)
        }
    }

    val latestPostsStream = repository.getLatestPostsStream()
        .map {
            TopPostsViewState.Loaded(it.toPostsViewData())
        }.asLiveData()

    fun setConnected(connected: Boolean) {
        this.connected = connected
        if (connected && _firstLoad) {
            viewModelScope.launch { repository.refreshLatestPosts() }
        }
    }

    fun setFirstLoad(firstLoad: Boolean) {
        _firstLoad = firstLoad
    }

    private fun List<Post>.toPostsViewData() =
        map { it.toPostViewData() }

    private fun Post.toPostViewData() =
        PostViewData(
            storyId = storyId,
            storyTitle = if (storyTitle.isNullOrBlank()) title else storyTitle,
            storyUrl = if (storyUrl.isNullOrBlank()) url else storyUrl,
            storySubTitle = "$author - ${formatUtil.formatCreatedAtString(createdAt)}"
        )

    sealed class TopPostsViewState {
        object WaitingForUserAction : TopPostsViewState()
        class Error(val message: String) : TopPostsViewState()
        class Loaded(val posts: List<PostViewData>) : TopPostsViewState()
    }
}

