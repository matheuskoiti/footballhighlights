package com.studiomk.footballhighlights.data.repository

import com.google.gson.GsonBuilder
import com.studiomk.footballhighlights.data.mapper.HighLightMapper
import com.studiomk.footballhighlights.data.service.FootBallService
import com.studiomk.footballhighlights.domain.model.HighLight
import io.reactivex.Single
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FootBallRepository: KoinComponent {

    private val highLightMapper: HighLightMapper by inject()

    var retrofit = Retrofit.Builder()
        .baseUrl("https://www.scorebat.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    var service = retrofit.create<FootBallService>(FootBallService::class.java)

    fun getFootballHighLights(): Single<List<HighLight>> {
        return service.getFootballHighLight().map {
            highLightMapper.map(it)
        }
    }
}