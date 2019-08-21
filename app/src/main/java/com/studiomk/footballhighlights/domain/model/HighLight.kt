package com.studiomk.footballhighlights.domain.model

import com.studiomk.footballhighlights.data.model.Competition
import com.studiomk.footballhighlights.data.model.HighLightVideoData
import com.studiomk.footballhighlights.data.model.Side
import java.io.Serializable

class HighLight(val title : String,
                val thumbnail: String,
                val date: String,
                val formattedDate: String,
                val side1: Side,
                val side2: Side,
                val competition: Competition,
                val video: List<HighLightVideo>) : Serializable