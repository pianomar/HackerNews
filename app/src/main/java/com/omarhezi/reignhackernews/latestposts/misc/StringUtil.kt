package com.omarhezi.reignhackernews.latestposts.misc

import android.content.Context
import androidx.annotation.StringRes

class StringUtil(private val context: Context) {
    fun getString(@StringRes stringResource: Int) =
        context.getString(stringResource)
}
