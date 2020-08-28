package com.uniting.android.Home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class CategoryAdapter(val context: Context, val categoryList: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.setItem(categoryList[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var layoutItem = view.findViewById<ConstraintLayout>(R.id.layout_item)

        fun setItem(type : String){
            when(type){
                "취미" -> {
                    layoutItem.setBackgroundColor(Color.GREEN)
                }
                "스터디" -> {
                    layoutItem.setBackgroundColor(Color.GRAY)
                }
                "고민상담" -> {
                    layoutItem.setBackgroundColor(Color.YELLOW)
                }
                "잡담" -> {
                    layoutItem.setBackgroundColor(Color.BLUE)
                }
            }
        }
    }
}