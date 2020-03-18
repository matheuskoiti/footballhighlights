package com.studiomk.footballhighlights.data.analytics

interface FirebaseAnalyticsService {
    fun logEvent(category: String, action: String, label: String)
}