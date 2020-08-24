package com.uniting.android.Room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class MyRoomAdapter(val context: Context, val roomList: ArrayList<MyRoomItem>) :
    RecyclerView.Adapter<MyRoomAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}