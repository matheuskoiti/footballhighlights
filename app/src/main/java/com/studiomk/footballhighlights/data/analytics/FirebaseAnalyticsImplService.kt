package com.studiomk.footballhighlights.data.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsImplService : FirebaseAnalyticsService {

    companion object {
        const val EVENT_CATEGORY = "eventCategory"
        const val EVENT_ACTION = "eventAction"
        const val EVENT_LABEL = "eventLabel"
    }

    lateinit var firebaseAnalytics: FirebaseAnalytics

    fun init(appApplication: Context): FirebaseAnalyticsImplService {
        firebaseAnalytics = FirebaseAnalytics.getInstance(appApplication)
        return this
    }

    override fun logEvent(category: String, action: String, label: String) {
        val params = Bundle()
        params.putString(EVENT_CATEGORY, category)
        params.putString(EVENT_ACTION, action)
        params.putString(EVENT_LABEL, label)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, params)
    }
}