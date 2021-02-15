package com.decorator1889.cripta.Fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.decorator1889.cripta.MainActivity
import com.decorator1889.cripta.R

class WebViewFragment : Fragment(){

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view_news, container, false)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val url = arguments!!.getString("Url")

        val webView = view.findViewById<WebView>(R.id.wbContent)

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility =View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.visibility =View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }

        }
        webView.settings.javaScriptEnabled = true

        val settings = webView.settings
        settings.domStorageEnabled = true

        webView.loadUrl(url.toString())

        val fragmentActivity = activity
        if (fragmentActivity != null) {
            fragmentActivity.findViewById<View>(R.id.navigation).visibility = View.GONE
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.showUpButton()
    }
}

