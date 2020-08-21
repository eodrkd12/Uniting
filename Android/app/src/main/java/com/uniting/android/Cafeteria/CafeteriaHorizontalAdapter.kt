package com.uniting.android.Cafeteria

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_horizontalcafeteria.view.*

class CafeteriaHorizontalAdapter(val activity: Activity, val cafeteriaList: ArrayList<CafeteriaItem.Cafeteria>) : RecyclerView.Adapter<CafeteriaHorizontalAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return cafeteriaList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaHorizontalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontalcafeteria, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var tags = ""

        Glide.with(holder.itemView)
            .load(cafeteriaList.get(position).imageSrc)
            .override(300, 300)
            .into(holder.cafeteriaImage)

        holder.cafeteriaTitle.text = cafeteriaList.get(position).name

        holder.itemView.setOnClickListener {
            var intent = Intent(activity, CafeteriaInformActivity::class.java)
            intent.putExtra("name",cafeteriaList.get(position).name)
            intent.putExtra("x", cafeteriaList.get(position).x)
            intent.putExtra("y", cafeteriaList.get(position).y)
            intent.putExtra("phone", cafeteriaList.get(position).phone)
            intent.putExtra("id", cafeteriaList.get(position).id)
            intent.putExtra("roadAddr", cafeteriaList.get(position).roadAddr)
            intent.putExtra("options", cafeteriaList.get(position).options)
            intent.putExtra("bizHourInfo", cafeteriaList.get(position).bizHourInfo)
            if(cafeteriaList.get(position).tags == null)
            {
                intent.putExtra("tags", "")
            }
            else
            {
                for(i in 0..cafeteriaList.get(position).tags!!.size-1)
                {
                    tags += "#" + cafeteriaList.get(position).tags!!.get(i) + " "
                }
                intent.putExtra("tags", tags)
            }
            activity.startActivity(intent)
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cafeteriaImage = view.image_cafeteria
        val cafeteriaTitle = view.text_cafeteria_title
        val cafeteriaStarPoint = view.text_point
    }
}