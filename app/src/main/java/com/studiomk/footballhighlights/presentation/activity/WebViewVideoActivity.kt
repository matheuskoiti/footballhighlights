package com.studiomk.footballhighlights.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.studiomk.footballhighlights.R
import com.studiomk.footballhighlights.presentation.WebViewVideoContract
import kotlinx.android.synthetic.main.activity_web_view_video.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class WebViewVideoActivity : AppCompatActivity() {

    companion object {
        const val VIDEO_URL_PARAM = "VIDEO_URL_PARAM"
        fun createIntent(context: Context, videoUrl: String): Intent {
            return Intent(context, WebViewVideoActivity::class.java)
                .putExtra(VIDEO_URL_PARAM, videoUrl)
        }
    }

    private val presenter : WebViewVideoContract.Presenter by inject { parametersOf(this) }
    private val url by lazy { intent?.getStringExtra(VIDEO_URL_PARAM) as String}

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_video)
        webview_video?.webViewClient = WebViewClient()
        webview_video?.setBackgroundColor(Color.TRANSPARENT)
        webview_video?.settings?.javaScriptEnabled = true
        webview_video?.settings?.domStorageEnabled = true
        webview_video?.settings?.saveFormData = false
        webview_video?.settings?.allowFileAccessFromFileURLs = true
        webview_video?.settings?.domStorageEnabled = true
        webview_video?.settings?.allowUniversalAccessFromFileURLs = true
        webview_video?.loadUrl(url)
    }
}
