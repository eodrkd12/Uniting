package com.uniting.android.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(val context: Context, val chatList: ArrayList<ChatItem>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    lateinit var beforeChatView : View
    lateinit var beforeChat : ChatItem

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        var item = chatList[position]

        var hideImageAndNickname = false

        if(position>0 && chatList[position].chat.user_id == chatList[position-1].chat.user_id) {
            hideImageAndNickname = true
        }

        var timeStr = ""
        val time = item.chat.chat_time!!.split(" ")[1].split(":")
        val hour = time[0].toInt()
        val min = time[1]
        if (hour < 12)
            timeStr = "오전 ${hour}:${min}"
        else {
            if (hour != 12)
                timeStr = "오후 ${hour - 12}:${min}"
            else
                timeStr = "오후 ${hour}:${min}"
        }

        if (item.chat.system_chat == 1)
            holder.setSystemChat(item.chat.chat_content)
        else {
            if (item.chat.user_id == "test")

                holder.setMyChat(
                    item.chat.chat_content,
                    timeStr,
                    (item.chat.unread_member.split("|").size-1).toString()
                )
            else {
                holder.setPartnerChat(
                    "",
                    item.chat.user_nickname,
                    item.chat.chat_content,
                    timeStr,
                    (item.chat.unread_member.split("|").size-1).toString(),
                    hideImageAndNickname
                )
            }
        }

        if(position > 0 && beforeChat.chat.user_id==item.chat.user_id){
            beforeChatView.text_time.visibility=View.GONE
            beforeChatView.text_partner_time.visibility=View.GONE
        }

        beforeChatView=holder.view
        beforeChat=item
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var textContent = view.text_content
        var textTime = view.text_time
        var textUnreadCount = view.text_unread_count

        var imagePartner = view.image_partner
        var textPartner = view.text_partner
        var textPartnerContent = view.text_partner_content
        var textPartnerTime = view.text_partner_time
        var textPartnerUnreadCount = view.text_partner_unread_count

        var textSystem = view.text_system

        fun setMyChat(content: String, time: String, unreadCount: String) {
            textContent.visibility = View.VISIBLE
            textTime.visibility = View.VISIBLE
            textUnreadCount.visibility = View.VISIBLE

            imagePartner.visibility = View.GONE
            textPartner.visibility = View.GONE
            textPartnerContent.visibility = View.GONE
            textPartnerTime.visibility = View.GONE
            textPartnerUnreadCount.visibility = View.GONE

            textSystem.visibility = View.GONE

            textContent.text = content
            textTime.text = time
            if(unreadCount == "0") textUnreadCount.text = ""
            else textUnreadCount.text = unreadCount
        }

        fun setPartnerChat(
            imageUrl: String,
            partner: String,
            content: String,
            time: String,
            unreadCount: String,
            hideImageAndNickname: Boolean
        ) {
            textContent.visibility = View.GONE
            textTime.visibility = View.GONE
            textUnreadCount.visibility = View.GONE

            if(hideImageAndNickname) {
                imagePartner.visibility = View.GONE
                textPartner.visibility = View.GONE
                var constraintSet = ConstraintSet()
                constraintSet.clone(view.findViewById<ConstraintLayout>(R.id.layout_chat))
                constraintSet.connect(
                    R.id.text_partner_content,
                    ConstraintSet.TOP,
                    R.id.layout_chat,
                    ConstraintSet.TOP,
                    8
                )
                constraintSet.applyTo(view.findViewById(R.id.layout_chat))
            }
            else {
                imagePartner.visibility = View.VISIBLE
                textPartner.visibility = View.VISIBLE
            }
            textPartnerContent.visibility = View.VISIBLE
            textPartnerTime.visibility = View.VISIBLE
            textPartnerUnreadCount.visibility = View.VISIBLE

            textSystem.visibility = View.GONE

            /*Glide.with(view)
                .load(imageUrl).apply(RequestOptions().fitCenter()).transition(
                    DrawableTransitionOptions().crossFade()
                )
                .apply(RequestOptions().override(640, 640))
                .into(imagePartner)
            imagePartner.setClipToOutline(true)*/
            textPartner.text = partner
            textPartnerContent.text = content
            textPartnerTime.text = time
            if(unreadCount == "0") textPartnerUnreadCount.text = ""
            else textPartnerUnreadCount.text = unreadCount
        }

        fun setSystemChat(content: String) {
            textContent.visibility = View.GONE
            textTime.visibility = View.GONE
            textUnreadCount.visibility = View.GONE

            imagePartner.visibility = View.GONE
            textPartner.visibility = View.GONE
            textPartnerContent.visibility = View.GONE
            textPartnerTime.visibility = View.GONE
            textPartnerUnreadCount.visibility = View.GONE

            textSystem.visibility = View.VISIBLE

            textSystem.text = content
        }
    }
}