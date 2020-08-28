package com.uniting.android.Home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class MatchingTypeAdapter(val context: Context, val matchingTypeList: ArrayList<String>) :
    RecyclerView.Adapter<MatchingTypeAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingTypeAdapter.ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_matching, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MatchingTypeAdapter.ViewHolder, position: Int) {

        holder.setItem(matchingTypeList[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var layoutItem = view.findViewById<ConstraintLayout>(R.id.layout_item)

        fun setItem(type : String){
            when(type){
                "일반매칭" -> {
                    layoutItem.setBackgroundColor(Color.GREEN)
                }
                "스마트매칭" -> {
                    layoutItem.setBackgroundColor(Color.GRAY)
                }
            }
        }
    }
}