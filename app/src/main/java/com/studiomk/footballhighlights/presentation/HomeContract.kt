package com.studiomk.footballhighlights.presentation

import com.studiomk.footballhighlights.domain.model.HighLight

class HomeContract {
    interface View {
        fun initListeners()
        fun buildHighLightList(list: List<HighLight>)
        fun showLoading()
        fun hideLoading()
        fun showErrorMessage()
        fun hideErrorMessage()
        fun openHighLight(highLight: HighLight)
        fun cleanSearchView()
        fun openHighLightActivity(highLight: HighLight)
    }

    interface Presenter {
        fun initPresenter()
        fun prepareHighLightOpening(highLight: HighLight)
    }
}