package com.uniting.android.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_chat.view.*

class MemberAdapter(val context: Context, val memberList: ArrayList<MemberItem>) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>()  {

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)

        return MemberAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int) {
        holder.textMember.setText("${memberList[position].member.nickname}")
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var imageMember = view.findViewById<ImageView>(R.id.image_member)
        var textMember = view.findViewById<TextView>(R.id.text_member)
    }
}