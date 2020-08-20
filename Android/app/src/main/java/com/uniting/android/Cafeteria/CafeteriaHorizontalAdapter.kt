package com.uniting.android.Cafeteria

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_horizontalcafeteria.view.*

class CafeteriaHorizontalAdapter(activity: Activity, val cafeteriaList: ArrayList<CafeteriaItem.Cafeteria>) : RecyclerView.Adapter<CafeteriaHorizontalAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return cafeteriaList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaHorizontalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontalcafeteria, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(cafeteriaList.get(position).imageSrc)
            .override(100, 100)
            .into(holder.cafeteriaImage)

        holder.cafeteriaTitle.text = cafeteriaList.get(position).name
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cafeteriaImage = view.image_cafeteria
        val cafeteriaTitle = view.text_cafeteria_title
        val cafeteriaStarPoint = view.text_point
    }
}