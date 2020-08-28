package com.uniting.android.Cafeteria

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.uniting.android.Class.GpsTracker
import com.uniting.android.R
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class ReviewAdapter(val activity: Activity, val reviewList:ArrayList<CafeteriaItem.Review>, val size : Int) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cafeteria_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val gpsTracker = GpsTracker(activity)
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val reviewDate = simpleDateFormat.parse(reviewList.get(position).reviewDate)

        holder.reviewNickname.text = reviewList.get(position).userNickname
        when(reviewList.get(position).reviewPoint) {
            1 -> holder.reviewPoint.text = "★☆☆☆☆"
            2 -> holder.reviewPoint.text = "★★☆☆☆"
            3 -> holder.reviewPoint.text = "★★★☆☆"
            4 -> holder.reviewPoint.text = "★★★★☆"
            5 -> holder.reviewPoint.text = "★★★★★"
        }
        holder.reviewDate.text = gpsTracker.timeDiff(reviewDate.time)

        if(reviewList.get(position).imageUrl == "null") {
            holder.reviewImage.visibility = View.GONE
        } else {
            Glide.with(holder.itemView)
                .load(reviewList.get(position).imageUrl)
                .transition(DrawableTransitionOptions().crossFade())
                .override(400, 400)
                .into(holder.reviewImage)
        }

        holder.reviewImage.clipToOutline = true

        holder.reviewContent.text = reviewList.get(position).reviewContent
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val reviewNickname : TextView = view.findViewById(R.id.text_review_nickname)
        val reviewPoint : TextView = view.findViewById(R.id.text_review_point)
        val reviewDate : TextView = view.findViewById(R.id.text_review_date)
        val reviewImage : ImageView = view.findViewById(R.id.image_review)
        val reviewContent : TextView = view.findViewById(R.id.text_review_content)
    }
}