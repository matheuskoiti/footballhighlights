package com.studiomk.footballhighlights.presentation.di

import android.content.Context
import android.content.SharedPreferences
import com.studiomk.footballhighlights.data.analytics.FirebaseAnalyticsImplService
import com.studiomk.footballhighlights.data.analytics.FirebaseAnalyticsService
import com.studiomk.footballhighlights.data.mapper.HighLightMapper
import com.studiomk.footballhighlights.data.repository.FootBallRepository
import com.studiomk.footballhighlights.domain.usecase.FootBallUseCase
import com.studiomk.footballhighlights.presentation.HighLightContract
import com.studiomk.footballhighlights.presentation.HomeContract
import com.studiomk.footballhighlights.presentation.WebViewVideoContract
import com.studiomk.footballhighlights.presentation.presenter.HighLightPresenter
import com.studiomk.footballhighlights.presentation.presenter.HomePresenter
import com.studiomk.footballhighlights.presentation.presenter.WebViewVideoPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val applicationModule = module(override = true) {
    factory<HomeContract.Presenter> { (view: HomeContract.View) -> HomePresenter(view) }
    factory<HighLightContract.Presenter> { (view: HighLightContract.View) -> HighLightPresenter(view) }
    factory<WebViewVideoContract.Presenter> { (view: WebViewVideoContract.View) -> WebViewVideoPresenter(view) }
    single { FootBallRepository() }
    single { FootBallUseCase() }
    single { HighLightMapper() }
    factory<FirebaseAnalyticsService> { FirebaseAnalyticsImplService().init(androidContext()) }
    single<SharedPreferences> { androidContext().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE) }
}