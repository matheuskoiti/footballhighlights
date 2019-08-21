package com.studiomk.footballhighlights.data.service

import com.studiomk.footballhighlights.data.model.HighLightData
import io.reactivex.Single
import retrofit2.http.GET

interface FootBallService {

    companion object {
        const val FOOTBALL_HIGH_LIGHT_API = "video-api/v1/"
    }

    @GET(FOOTBALL_HIGH_LIGHT_API)
    fun getFootballHighLight() : Single<List<HighLightData>>
}