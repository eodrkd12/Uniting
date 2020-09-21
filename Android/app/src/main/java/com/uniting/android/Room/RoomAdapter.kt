package com.uniting.android.Room

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.uniting.android.Chat.ChatActivity
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Room
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.item_room.view.*
import java.text.SimpleDateFormat

class RoomAdapter(val context: Context, val roomList: ArrayList<RoomItem>) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    var ref: DatabaseReference? = null
    var query: Query? = null
    var lastChatTime: String? = null

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)

        return ViewHolder(context, itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        ref = FirebaseDatabase.getInstance().reference.child("chat")
            .child(roomList[position].room.room_id)
        query = ref!!.orderByChild("chat_time").limitToLast(1)
        query!!.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var value = p0.value as HashMap<String, Any>

                lastChatTime = value["chat_time"] as String
                holder.setItem(roomList[position], lastChatTime!!)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }

    class ViewHolder(val context: Context, val view: View) : RecyclerView.ViewHolder(view) {

        fun setItem(roomItem: RoomItem, lastChatTime: String) {
            view.text_title.text = roomItem.room.room_title
            view.text_introduce.text = roomItem.room.room_introduce
            view.text_num_member.text = roomItem.numOfMembers.toString() + "명 참가중"
            view.btn_enter.setOnClickListener {
                Retrofit.joinCheck(roomItem.room.room_id, UserInfo.ID) {
                    if (it.count == 0) {
                        //방 입장
                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        var curDate = simpleDateFormat.format(System.currentTimeMillis())
                        Retrofit.joinRoom(roomItem.room.room_id, UserInfo.ID, curDate) {
                            if(it.result == "success"){
                                Retrofit.addUnreadMember(roomItem.room.room_id, UserInfo.ID){
                                    if(it.result == "success") {

                                        var ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomItem.room.room_id)
                                        var query = ref.orderByChild("chat_time")
                                        query!!.addChildEventListener(object : ChildEventListener {
                                            override fun onCancelled(error: DatabaseError) {
                                            }

                                            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                                            }

                                            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                                            }

                                            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                                                var key = snapshot.key
                                                var value = snapshot.value as HashMap<String,Any>
                                                val hashMap = HashMap<String,Any>()

                                                hashMap.put("${key}/unread_member",value["unread_member"].toString() + "${UserInfo.ID}|")
                                                ref.updateChildren(hashMap)
                                            }

                                            override fun onChildRemoved(snapshot: DataSnapshot) {
                                            }
                                        })


                                        var intent = Intent(context, ChatActivity::class.java)
                                        intent.putExtra("room", roomItem.room)
                                        intent.putExtra("last_chat_time", lastChatTime)
                                        context.startActivity(intent)
                                    }
                                }
                            }
                        }
                    } else {
                        //입장중인 방 알림
                        var builder = AlertDialog.Builder(context)
                        builder.setTitle("참여중인 방입니다.")
                            .setMessage("입장하시겠습니까?")
                            .setPositiveButton("입장", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    var intent = Intent(context, ChatActivity::class.java)
                                    intent.putExtra("room", roomItem.room)
                                    intent.putExtra("last_chat_time", lastChatTime)
                                    context.startActivity(intent)
                                }
                            })
                            .setNegativeButton("취소", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                }
                            })
                            .show()
                    }
                }
            }
        }
    }
}