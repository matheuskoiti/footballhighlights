package com.studiomk.footballhighlights.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.studiomk.footballhighlights.R
import kotlinx.android.synthetic.main.home_highlight_item.view.*
import com.squareup.picasso.Picasso
import com.studiomk.footballhighlights.domain.model.HighLight
import com.studiomk.footballhighlights.presentation.activity.HomeActivity

class HighLightsAdapter(val context : Context, private val highLightDataList: List<HighLight>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER_VIEW_TYPE = 0
    private val ITEM_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_VIEW_TYPE) {
            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_highlight_header, parent, false)
            HighLightHeaderViewHolder(textView)
        } else {
            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_highlight_item, parent, false)
            HighLightViewHolder(textView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HighLightViewHolder) {
            val currentHighLight = highLightDataList[position - 1]
            Picasso.with(context)
                .load(currentHighLight.thumbnail)
                .resize(100,100).into(holder.itemThumbnail)
            holder.itemTitle.text = currentHighLight.title
            holder.itemCompetition.text = currentHighLight.competition.name
            holder.itemDate.text = currentHighLight.formattedDate
            holder.itemLayout.setOnClickListener {
                (context as HomeActivity).openHighLightActivity(currentHighLight)
            }
        }
    }

    override fun getItemCount() = highLightDataList.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == HEADER_VIEW_TYPE) {
            return HEADER_VIEW_TYPE
        }
        return ITEM_VIEW_TYPE
    }

    class HighLightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemLayout: ConstraintLayout = view.home_highlight_item_layout
        val itemTitle : TextView = view.home_highlight_item_title
        val itemThumbnail : ImageView = view.home_highlight_item_thumbnail
        val itemCompetition : TextView = view.home_highlight_item_competition
        val itemDate : TextView = view.home_highlight_item_date
    }

    class HighLightHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}