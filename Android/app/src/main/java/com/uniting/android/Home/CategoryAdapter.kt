package com.uniting.android.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class CategoryAdapter(val context: Context, val categoryList: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.setItem(categoryList[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var textTitle = view.findViewById<TextView>(R.id.text_condition_title)
        var textContent = view.findViewById<TextView>(R.id.text_content)

        fun setItem(type : String){
            when(type){
                "취미" -> {
                    textTitle.setText("취미")
                    textContent.setText("사람들과 취미를 공유해보세요")
                }
                "스터디" -> {
                    textTitle.setText("스터디")
                    textContent.setText("사람들과 스터디를 해보세요")
                }
                "고민상담" -> {
                    textTitle.setText("고민상담")
                    textContent.setText("사람들에게 고민상담을 해보세요")
                }
                "잡담" -> {
                    textTitle.setText("잡담")
                    textContent.setText("사람들과 이야기를 나눠보세요")
                }
            }
        }
    }
}