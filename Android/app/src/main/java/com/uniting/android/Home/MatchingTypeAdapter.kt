package com.uniting.android.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class MatchingTypeAdapter(val context: Context, val matchingTypeList: ArrayList<String>) :
    RecyclerView.Adapter<MatchingTypeAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingTypeAdapter.ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_matching, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MatchingTypeAdapter.ViewHolder, position: Int) {

        holder.setItem(matchingTypeList[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var textTitle = view.findViewById<TextView>(R.id.layout_room)
        var textContent = view.findViewById<TextView>(R.id.text_content)

        fun setItem(type : String){
            when(type){
                "일반매칭" -> {
                    textTitle.setText("일반 매칭")
                    textContent.setText("유니팅에 가입한 상대방과 매칭")
                }
                "스마트매칭" -> {
                    textTitle.setText("스마트 매칭")
                    textContent.setText("유니팅에 가입한 상대방 스마트하게 매칭(유료)")
                }
            }
        }
    }
}