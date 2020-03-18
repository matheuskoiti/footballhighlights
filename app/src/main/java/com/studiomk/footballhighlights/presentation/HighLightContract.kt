package com.studiomk.footballhighlights.presentation

import com.studiomk.footballhighlights.domain.model.HighLightVideo

class HighLightContract {
    interface View {
        fun onItemClick(highLightVideo: HighLightVideo)
        fun openWebViewActivity(highLightVideo: HighLightVideo)
    }

    interface Presenter {
        fun prepareWebView(highLightVideo: HighLightVideo)
    }
}