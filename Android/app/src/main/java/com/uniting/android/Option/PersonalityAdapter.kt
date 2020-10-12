package com.uniting.android.Option

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Login.UserItem
import com.uniting.android.R

class PersonalityAdapter(val context: Context, val userOptionList:ArrayList<UserItem.UserOption>) : RecyclerView.Adapter<PersonalityAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userOptionList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalityAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_condition, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userOption.text = userOptionList.get(position).title

        if(userOptionList.get(position).isSelected == true) {
            holder.userOption.setTextColor(Color.parseColor("#00BFFF"))
        }
        else {
            holder.userOption.setTextColor(Color.parseColor("#9E9E9E"))
        }

        holder.itemView.setOnClickListener {
            userOptionList.get(position).isSelected = !userOptionList.get(position).isSelected

            if(userOptionList.get(position).isSelected == true) {
                holder.userOption.setTextColor(Color.parseColor("#00BFFF"))
            }
            else {
                holder.userOption.setTextColor(Color.parseColor("#9E9E9E"))
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val userOption : TextView = view.findViewById(R.id.text_condition)
    }
}