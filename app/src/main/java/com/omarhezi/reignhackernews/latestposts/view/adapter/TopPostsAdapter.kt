package com.omarhezi.reignhackernews.latestposts.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.omarhezi.reignhackernews.latestposts.view.models.PostViewData

class TopPostsAdapter(private val itemSelectionListener: PostSelectionListener? = null) :
    ListAdapter<PostViewData, PostViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostViewHolder.from(parent, itemSelectionListener)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun removeItem(adapterPosition: Int) {

    }

    class DiffCallback : DiffUtil.ItemCallback<PostViewData>() {
        override fun areItemsTheSame(oldItem: PostViewData, newItem: PostViewData) =
            oldItem.storyId == newItem.storyId

        override fun areContentsTheSame(oldItem: PostViewData, newItem: PostViewData) =
            oldItem == newItem
    }
}

interface PostSelectionListener {
    fun onPostSelected(post: PostViewData)
}