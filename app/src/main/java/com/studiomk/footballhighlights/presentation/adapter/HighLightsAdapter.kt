package com.studiomk.footballhighlights.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.studiomk.footballhighlights.R
import kotlinx.android.synthetic.main.home_highlight_item.view.*
import com.squareup.picasso.Picasso
import com.studiomk.footballhighlights.domain.model.HighLight
import com.studiomk.footballhighlights.presentation.activity.HomeActivity

class HighLightsAdapter(val context : Context,
                        private var highLightDataList: List<HighLight>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val HEADER_VIEW_TYPE = 0
    private val ITEM_VIEW_TYPE = 1
    private var highLightDataListFiltered: List<HighLight> = highLightDataList
    private val completeHighLightDataListFiltered: List<HighLight> = highLightDataList


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
                (context as HomeActivity).openHighLight(currentHighLight)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    highLightDataList = completeHighLightDataListFiltered
                } else {
                    val filteredList = arrayListOf<HighLight>()
                    for (row in completeHighLightDataListFiltered) {
                        if (row.competition.name.toLowerCase().contains(charString.toLowerCase()) || row.title.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    highLightDataList = filteredList

                }

                val filterResults = FilterResults()
                filterResults.values = highLightDataListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                if (highLightDataList.isEmpty())  {
                    showNoResultsFound()
                }
                highLightDataListFiltered = filterResults.values as ArrayList<HighLight>
                notifyDataSetChanged()
            }
        }
    }

    private fun showNoResultsFound() {
        (context as HomeActivity).showEmptySearchResultText()
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