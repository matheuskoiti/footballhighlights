package com.studiomk.footballhighlights.data.model

class HighLightData(val title : String,
                    val thumbnail: String,
                    val date: String,
                    val side1: Side,
                    val side2: Side,
                    val competition: Competition,
                    val videos: List<HighLightVideoData>)