package com.uniting.android.Option

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Class.PSDialog
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_inquire.view.*

class InquireListAdapter(val context: Context, val inquireList:ArrayList<InquireItem.Inquire>) : RecyclerView.Adapter<InquireListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return inquireList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InquireListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_inquire, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(inquireList.get(position).inquireType) {
            "qna" -> {
                holder.itemView.text_inquiretitle.text = "[문의] " + inquireList.get(position).inquireTitle
            }
            "report" -> {
                holder.itemView.text_inquiretitle.text = "[신고] " + inquireList.get(position).inquireTitle
            }
        }

        when(inquireList.get(position).inquireCheck) {
            0 -> {
                holder.itemView.text_inquirecheck.text ="답변대기"
            }
            1 -> {
                holder.itemView.text_inquirecheck.text ="답변완료"
                holder.itemView.text_inquirecheck.setTextColor(Color.RED)
            }
        }

        holder.itemView.text_inquiredate.text = inquireList.get(position).inquireDate

        holder.itemView.setOnClickListener {
            if(inquireList.get(position).inquireCheck == 1) {
                val psDialog = PSDialog(context as Activity)
                psDialog.setInquireClickDialog(inquireList.get(position).inquireTitle, inquireList.get(position).inquireContent, inquireList.get(position).inquireAnswer, inquireList.get(position).inquireDate, inquireList.get(position).answerDate)
                psDialog.show()
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}