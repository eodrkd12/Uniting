package com.uniting.android.Chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class ChatAdapter(val context: Context, val chatList: ArrayList<ChatItem>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_chat, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        var chatItem = chatList[position]


    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    fun clear(){
        chatList.clear()
    }
}