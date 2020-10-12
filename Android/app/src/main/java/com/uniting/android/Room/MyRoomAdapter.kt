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
import com.uniting.android.R

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
            roomTitle.text = myRoom.room.room_title
            lastChat.text = ""
            lastChatTime.text = ""
            Log.d("test","${position}, ${myRoom.room.room_id}")
            var ref = FirebaseDatabase.getInstance().reference.child("chat").child(myRoom.room.room_id)
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

                    myRoom.lastChatTime = value["chat_time"] as String
                    myRoom.lastChat = value["chat_content"] as String

                    var time = myRoom.lastChatTime.split(" ")[1]
                    var hour = time.split(":")[0]

                    if(time != "") {
                        if (hour.toInt() > 12) hour = "오후 ${hour.toInt() - 12}:${time.split(":")[1]}"
                        else hour = "오전 ${hour}:${time.split(":")[1]}"

                        lastChatTime.text = hour
                        lastChat.text = myRoom.lastChat
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                }
            })

            view.setOnClickListener {
                var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("room",myRoom.room)
                intent.putExtra("last_chat_time",myRoom.lastChatTime)
                context.startActivity(intent)
            }
        }

    }

    fun sortByLastChat() {
        roomList.sortByDescending { selector(it) }
    }

    fun selector(room: MyRoomItem): String = room.lastChatTime
}