package com.studiomk.footballhighlights.data.mapper

import com.studiomk.footballhighlights.data.model.HighLightData
import com.studiomk.footballhighlights.data.model.HighLightVideoData
import com.studiomk.footballhighlights.domain.model.HighLight
import com.studiomk.footballhighlights.domain.model.HighLightVideo
import java.text.SimpleDateFormat
import java.util.*

class HighLightMapper {

    fun map(highLightData: List<HighLightData>): List<HighLight> {
        val list = mutableListOf<HighLight>()
        highLightData.map {
            list.add(HighLight(title = it.title,
                competition = it.competition,
                date = it.date,
                formattedDate = formatDate(it.date),
                side1 = it.side1,
                side2 = it.side2,
                thumbnail = it.thumbnail,
                video = mapVideo(it.videos)))
        }
        return list
    }

    private fun mapVideo(highLightVideoData: List<HighLightVideoData>): List<HighLightVideo> {
        val list = mutableListOf<HighLightVideo>()
        highLightVideoData.map {
            list.add(HighLightVideo(title = changeHighLightString(it.title),
                embed = it.embed,
                videoUrl = getVideoUrlFromEmbed(it.embed)))
        }
        return list
    }

    private fun formatDate(date: String): String {
        val myLocale = Locale("pt", "BR")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", myLocale).parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat
        return SimpleDateFormat("dd/MM/yyyy", myLocale).format(calendar.time)
    }

    private fun getVideoUrlFromEmbed(embed: String): String {
        return embed.substringAfter("src='").substringBefore("?s=2'")
    }

    private fun changeHighLightString(value: String): String {
        if (value.toLowerCase() == "highlights") {
            return "Melhores momentos"
        }
        return value
    }
}
