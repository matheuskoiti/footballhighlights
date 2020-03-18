package com.studiomk.footballhighlights.presentation.presenter

import com.studiomk.footballhighlights.domain.model.HighLightVideo
import com.studiomk.footballhighlights.presentation.HighLightContract
import org.koin.standalone.KoinComponent

class HighLightPresenter(var view: HighLightContract.View) : HighLightContract.Presenter, KoinComponent {

    override fun prepareWebView(highLightVideo: HighLightVideo) {
        view.openWebViewActivity(highLightVideo)
    }
}