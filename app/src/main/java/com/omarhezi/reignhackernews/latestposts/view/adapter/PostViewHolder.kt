package com.omarhezi.reignhackernews.latestposts.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omarhezi.reignhackernews.R
import com.omarhezi.reignhackernews.latestposts.view.models.PostViewData
import kotlinx.android.synthetic.main.post_listitem.view.*

class PostViewHolder(
    itemView: View,
    private val itemSelectionListener: PostSelectionListener?
) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: PostViewData) {
        itemView.postTitle.text = item.storyTitle
        itemView.postSubText.text = item.storySubTitle

        itemView.postItemRoot.setOnClickListener {
            itemSelectionListener?.onPostSelected(item)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            itemSelectionListener: PostSelectionListener?
        ): PostViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.post_listitem, parent, false)
            return PostViewHolder(view, itemSelectionListener)
        }
    }
}