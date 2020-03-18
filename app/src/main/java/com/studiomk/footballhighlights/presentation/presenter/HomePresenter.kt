package com.studiomk.footballhighlights.presentation.presenter

import com.studiomk.footballhighlights.data.analytics.FirebaseAnalyticsService
import com.studiomk.footballhighlights.domain.model.HighLight
import com.studiomk.footballhighlights.domain.usecase.FootBallUseCase
import com.studiomk.footballhighlights.presentation.HomeContract
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class HomePresenter(private var view: HomeContract.View) : HomeContract.Presenter, KoinComponent {

    private val footBallUseCase : FootBallUseCase by inject()
    private val firebaseAnalyticsService : FirebaseAnalyticsService by inject()

    override fun initPresenter() {
        fetchFootBallHighLights()
        view.initListeners()
    }

    private fun fetchFootBallHighLights() {
        view.showLoading()
        footBallUseCase.getHighLights(observer = object : SingleObserver<List<HighLight>> {
            override fun onSuccess(highLightDataList: List<HighLight>) {
                view.buildHighLightList(highLightDataList)
                view.hideErrorMessage()
                view.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                view.showErrorMessage()
                view.hideLoading()
            }
        })
    }

    override fun prepareHighLightOpening(highLight: HighLight) {
        firebaseAnalyticsService.logEvent(category = "highLight", action = "click-highLight", label = "${highLight.competition.name}:${highLight.title}")
        view.openHighLightActivity(highLight)
    }
}