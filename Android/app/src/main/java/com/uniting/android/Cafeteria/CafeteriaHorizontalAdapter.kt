package com.uniting.android.Cafeteria

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.item_horizontalcafeteria.view.*

class CafeteriaHorizontalAdapter(val activity: Activity, val cafeteriaList: ArrayList<CafeteriaItem.CafeteriaPreview>) : RecyclerView.Adapter<CafeteriaHorizontalAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return cafeteriaList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaHorizontalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontalcafeteria, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.cafeteriaImage.clipToOutline = true

        holder.cafeteriaTitle.text = cafeteriaList.get(position).cafeteriaName.replace(" ", "")
        holder.cafeteriaStarPoint.text = cafeteriaList.get(position).starPoint.toString()


        holder.itemView.setOnClickListener {
            var intent = Intent(activity, CafeteriaInformActivity::class.java)
            activity.startActivity(intent)
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cafeteriaImage = view.image_cafeteria
        val cafeteriaTitle = view.text_cafeteria_title
        val cafeteriaStarPoint = view.text_point
    }
}
