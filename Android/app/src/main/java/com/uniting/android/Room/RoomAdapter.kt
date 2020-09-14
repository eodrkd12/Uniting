package com.uniting.android.Room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.item_room.view.*

class RoomAdapter(val context: Context, val roomList: ArrayList<RoomItem>) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setItem(roomList[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setItem(roomItem: RoomItem){
            view.text_title.text=roomItem.room.room_title
            view.text_introduce.text=roomItem.room.room_introduce
            view.text_num_member.text=roomItem.numOfMembers.toString()
            view.btn_enter.setOnClickListener {
            }
        }
    }
}