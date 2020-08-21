package com.uniting.android.Cafeteria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class MenuAdapter(val menuList:ArrayList<CafeteriaItem.Menu>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        holder.menuTitle.text = menuList.get(position).name
        holder.menuPrice.text = "· " + menuList.get(position).price
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val menuTitle : TextView = view.findViewById(R.id.text_menu)
        val menuPrice : TextView = view.findViewById(R.id.text_price)
    }
}