package com.uniting.android.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.uniting.android.Class.UserInfo
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatListAdapter(val context: Context, val chatList: ArrayList<ChatItem>) : BaseAdapter() {

    var view : View? = null


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        val holder : ViewHolder

        if(convertView == null){
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_chat, parent, false)
            holder = ViewHolder(view)

            holder.textContent = view.findViewById(R.id.text_content)
            holder.textTime = view.findViewById(R.id.text_time)
            holder.textUnreadCount = view.findViewById(R.id.text_unread_count)

            holder.textPartner = view.findViewById(R.id.text_partner)
            holder.textPartnerContent = view.findViewById(R.id.text_partner_content)
            holder.textPartnerTime = view.findViewById(R.id.text_partner_time)
            holder.textPartnerUnreadCount = view.findViewById(R.id.text_partner_unread_count)

            holder.textSystem = view.findViewById(R.id.text_system)

            view.tag = holder
        }
        else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }


        var item = chatList[position]

        var hideImageAndNickname = false

        if(position>0 && chatList[position].chat.user_id == chatList[position-1].chat.user_id) {
            hideImageAndNickname = true
        }

        var timeStr = ""
        var time = item.chat.chat_time!!.split(" ")[1].split(":")
        var hour = time[0].toInt()
        var min = time[1]
        if (hour < 12)
            timeStr = "오전 ${hour}:${min}"
        else {
            if (hour != 12)
                timeStr = "오후 ${hour - 12}:${min}"
            else
                timeStr = "오후 ${hour}:${min}"
        }

        if(position < chatList.lastIndex){
            var nextTimeStr = ""
            time = chatList[position+1].chat.chat_time!!.split(" ")[1].split(":")
            hour = time[0].toInt()
            min = time[1]
            if (hour < 12)
                nextTimeStr = "오전 ${hour}:${min}"
            else {
                if (hour != 12)
                    nextTimeStr = "오후 ${hour - 12}:${min}"
                else
                    nextTimeStr = "오후 ${hour}:${min}"
            }
            if(chatList[position].chat.user_id == chatList[position+1].chat.user_id && timeStr == nextTimeStr)
                timeStr = ""
        }

        if (item.chat.system_chat == 1)
            holder.setSystemChat(item.chat.chat_content)
        else {
            if (item.chat.user_id == UserInfo.ID)

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

        return view
    }

    override fun getItem(p0: Int): Any {
        return chatList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return chatList.size
    }

    class ViewHolder(val view: View){
        var textContent : TextView? = null
        var textTime  : TextView? = null
        var textUnreadCount  : TextView? = null

        var textPartner  : TextView? = null
        var textPartnerContent  : TextView? = null
        var textPartnerTime  : TextView? = null
        var textPartnerUnreadCount  : TextView? = null

        var textSystem  : TextView? = null

        fun setMyChat(content: String, time: String, unreadCount: String) {
            textContent!!.visibility = View.VISIBLE
            textTime!!.visibility = View.VISIBLE
            textUnreadCount!!.visibility = View.VISIBLE

            textPartner!!.visibility = View.GONE
            textPartnerContent!!.visibility = View.GONE
            textPartnerTime!!.visibility = View.GONE
            textPartnerUnreadCount!!.visibility = View.GONE

            textSystem!!.visibility = View.GONE

            textContent!!.text = content
            textTime!!.text = time
            if(unreadCount == "0") textUnreadCount!!.text = ""
            else textUnreadCount!!.text = unreadCount
        }

        fun setPartnerChat(
            imageUrl: String,
            partner: String,
            content: String,
            time: String,
            unreadCount: String,
            hideImageAndNickname: Boolean
        ) {
            textContent!!.visibility = View.GONE
            textTime!!.visibility = View.GONE
            textUnreadCount!!.visibility = View.GONE

            if(hideImageAndNickname) {
                textPartner!!.visibility = View.GONE
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
                textPartner!!.visibility = View.VISIBLE
            }
            textPartnerContent!!.visibility = View.VISIBLE
            textPartnerTime!!.visibility = View.VISIBLE
            textPartnerUnreadCount!!.visibility = View.VISIBLE

            textSystem!!.visibility = View.GONE

            textPartner!!.text = partner
            textPartnerContent!!.text = content
            textPartnerTime!!.text = time
            if(unreadCount == "0") textPartnerUnreadCount!!.text = ""
            else textPartnerUnreadCount!!.text = unreadCount
        }

        fun setSystemChat(content: String) {
            textContent!!.visibility = View.GONE
            textTime!!.visibility = View.GONE
            textUnreadCount!!.visibility = View.GONE

            textPartner!!.visibility = View.GONE
            textPartnerContent!!.visibility = View.GONE
            textPartnerTime!!.visibility = View.GONE
            textPartnerUnreadCount!!.visibility = View.GONE

            textSystem!!.visibility = View.VISIBLE

            textSystem!!.text = content
        }
    }

    fun sortByChatTime() {
        chatList.sortBy { selector(it) }
    }

    fun selector(chat: ChatItem): String = chat.chat.chat_time
}