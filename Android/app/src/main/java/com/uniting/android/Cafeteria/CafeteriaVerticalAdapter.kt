package com.uniting.android.Cafeteria

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class CafeteriaVerticalAdapter(activity: Activity, val cafeteriaType: ArrayList<String>) : RecyclerView.Adapter<CafeteriaVerticalAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return cafeteriaType.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaVerticalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_verticalcafeteria, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CafeteriaVerticalAdapter.ViewHolder, position: Int) {
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }
}