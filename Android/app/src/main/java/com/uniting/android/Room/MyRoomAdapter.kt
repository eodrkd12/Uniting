package com.uniting.android.Room

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.uniting.android.Chat.ChatActivity
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Room
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class MyRoomAdapter(val context: Context, val roomList: ArrayList<MyRoomItem>) :
    RecyclerView.Adapter<MyRoomAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_myroom, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(roomList[position], context)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var roomTitle = view.findViewById<TextView>(R.id.text_title)
        var lastChat = view.findViewById<TextView>(R.id.text_last_chat)
        var lastChatTime = view.findViewById<TextView>(R.id.text_last_chat_time)

        fun bind(myRoom: MyRoomItem, context: Context) {
            if(myRoom.category == "데이팅"){
                if(UserInfo.ID == myRoom.maker){
                    roomTitle.text = myRoom.room_title.split("&")[1]
                }
                else{
                    roomTitle.text = myRoom.room_title.split("&")[0]
                }
            }
            else {
                roomTitle.text = myRoom.room_title
            }


            if(myRoom.chat_time != "") {
                var time = myRoom.chat_time!!.split(" ")[1]
                var hour = time.split(":")[0]
                if (hour.toInt() > 12) hour = "오후 ${hour.toInt() - 12}:${time.split(":")[1]}"
                else hour = "오전 ${hour}:${time.split(":")[1]}"

                lastChat.text = myRoom.chat_content
                lastChatTime.text = hour
            }

            var ref = FirebaseDatabase.getInstance().reference.child("chat").child(myRoom.room_id)
            var query = ref!!.orderByChild("chat_time").limitToLast(1)
            query!!.addChildEventListener(object : ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    var value = p0.value as HashMap<String, Any>

                    var systemChat = value["system_chat"] as Long
                    if(systemChat == 0L){
                        myRoom.chat_time = value["chat_time"] as String
                        myRoom.chat_content = value["chat_content"] as String

                        var time = myRoom.chat_time!!.split(" ")[1]
                        var hour = time.split(":")[0]

                        if(time != "") {
                            if (hour.toInt() > 12) hour = "오후 ${hour.toInt() - 12}:${time.split(":")[1]}"
                            else hour = "오전 ${hour}:${time.split(":")[1]}"

                            lastChatTime.text = hour
                            lastChat.text = myRoom.chat_content
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                }
            })

            view.setOnClickListener {
                Retrofit.getEnterDate(myRoom.room_id, UserInfo.ID){
                    var intent = Intent(context, ChatActivity::class.java)
                    var room = Room(myRoom.room_id, myRoom.room_title, myRoom.maker, myRoom.category, myRoom.room_date, myRoom.room_introduce, myRoom.univ_name)
                    intent.putExtra("room",room)
                    intent.putExtra("enterDate",it.enter_date)
                    context.startActivity(intent)
                }
            }
        }

    }

    fun sortByLastChat() {
        roomList.sortByDescending { selector(it) }
    }

    fun selector(room: MyRoomItem): String = room.chat_time!!
}