package com.studiomk.footballhighlights.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.studiomk.footballhighlights.R
import com.studiomk.footballhighlights.data.model.HighLightVideoData
import com.studiomk.footballhighlights.domain.model.HighLightVideo
import com.studiomk.footballhighlights.presentation.activity.HighLightActivity
import kotlinx.android.synthetic.main.highlight_video_header.view.*
import kotlinx.android.synthetic.main.highlight_video_item.view.*
import org.w3c.dom.Text

class HighLightsVideoAdapter(val context : Context, private val highLightVideoDataList: List<HighLightVideo>, private val title: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER_VIEW_TYPE = 0
    private val ITEM_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_VIEW_TYPE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.highlight_video_header, parent,false)
            HighLightVideoHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.highlight_video_item, parent,false)
            HighLightVideoViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HighLightVideoViewHolder) {
            val currentHighLightVideo = highLightVideoDataList[position - 1]
            holder.itemTitle.text = currentHighLightVideo.title
            holder.itemLayout.setOnClickListener {
                (context as HighLightActivity).onItemClick(currentHighLightVideo.videoUrl)
            }
        } else if (holder is HighLightVideoHeaderViewHolder) {
            holder.headerTitle.text = title
        }
    }

    override fun getItemCount() = highLightVideoDataList.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == HEADER_VIEW_TYPE) {
            return HEADER_VIEW_TYPE
        }

        return ITEM_VIEW_TYPE
    }

    class HighLightVideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemTitle : TextView = view.highlight_video_item_title
        val itemLayout: ConstraintLayout = view.home_highlight_video_item_layout
    }

    class HighLightVideoHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerTitle : TextView = view.highlight_video_header_title
    }
}