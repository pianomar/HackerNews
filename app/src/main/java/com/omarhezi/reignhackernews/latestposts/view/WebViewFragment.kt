package com.omarhezi.reignhackernews.latestposts.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.omarhezi.reignhackernews.R
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlinx.android.synthetic.main.fragment_web_view.view.*

class WebViewFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)

        view.backButton.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = requireArguments().getString(URL_KEY)!!

        with(webview) {
            @SuppressLint("SetJavaScriptEnabled")
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }
            }

            loadUrl(url)
        }
    }

    companion object {
        private const val URL_KEY = "URL_KEY"

        fun create(
            url: String
        ): WebViewFragment {
            val fragment = WebViewFragment()
            val arguments = Bundle()
            arguments.putString(URL_KEY, url)
            fragment.arguments = arguments
            return fragment
        }
    }
}