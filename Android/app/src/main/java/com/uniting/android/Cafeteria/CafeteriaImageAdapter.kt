package com.uniting.android.Cafeteria

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.uniting.android.R

class CafeteriaImageAdapter(val activity: Activity, val imageList:ArrayList<String>, val size: Int) : RecyclerView.Adapter<CafeteriaImageAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaImageAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cafeteria_image, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CafeteriaImageAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(imageList.get(position))
            .transition(DrawableTransitionOptions().crossFade())
            .override(400, 400)
            .into(holder.cafeteriaImage)

        holder.cafeteriaImage.clipToOutline = true

        if(position == size-1) {
            holder.cafeteriaImage.setOnClickListener{
                Toast.makeText(activity, "마지막위치입니다.", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            holder.cafeteriaImage.setOnClickListener {
                Toast.makeText(activity, "${position}위치 입니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cafeteriaImage: ImageView = view.findViewById(R.id.image_cafeteria_inform)
    }
}