package com.omarhezi.reignhackernews.latestposts.core.viewmodel

import androidx.lifecycle.*
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

    var allPosts = listOf<PostViewData>()
    private val _viewState =
        MutableLiveData<TopPostsViewState>(TopPostsViewState.WaitingForUserAction)
    val viewState: LiveData<TopPostsViewState>
        get() = _viewState

    init {
        getLatestPosts()
    }

    val latestPostsStream = repository.getLatestPostsStream()
        .map {
            allPosts = it.toPostsViewData()
            TopPostsViewState.Loaded(allPosts)
        }.asLiveData()

    fun refreshPosts() {
        viewModelScope.launch {
            _viewState.value = TopPostsViewState.Loading
            handlePostsRequest {
                repository.refreshLatestPosts()
            }
            _viewState.value = TopPostsViewState.WaitingForUserAction
        }
    }

    fun getLatestPosts() {
        if (viewState.value is TopPostsViewState.WaitingForUserAction) {
            viewModelScope.launch {
                handlePostsRequest {
                    repository.getLatestPosts()
                }
            }
        }
    }

    fun deletePost(postPosition: Int) {
        allPosts[postPosition].storyId?.let {
            viewModelScope.launch {
                repository.deletePost(it)
            }
        }
    }

    private inline fun handlePostsRequest(request: () -> ResponseResult<List<Post>>) {
        _viewState.value = TopPostsViewState.Loading

        when (val postsResult = request.invoke()) {
            is ResponseResult.Error ->
                _viewState.value = TopPostsViewState.Error(postsResult.message ?: "")
        }

        _viewState.value = TopPostsViewState.WaitingForUserAction

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
        object Loading : TopPostsViewState()
        object WaitingForUserAction : TopPostsViewState()
        class Error(val message: String) : TopPostsViewState()
        class Loaded(val posts: List<PostViewData>) : TopPostsViewState()
    }
}

