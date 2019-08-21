package com.studiomk.footballhighlights.domain.usecase

import com.studiomk.footballhighlights.data.repository.FootBallRepository
import com.studiomk.footballhighlights.domain.model.HighLight
import io.reactivex.SingleObserver
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FootBallUseCase : BaseUseCase(), KoinComponent {

    private val repository: FootBallRepository by inject()

    fun getHighLights(observer: SingleObserver<List<HighLight>>) {
        repository.getFootballHighLights()
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
            .subscribe(observer)
    }
}