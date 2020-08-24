package com.uniting.android.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat

class ChatActivity : AppCompatActivity() {



    lateinit var chatViewModel : ChatViewModel

    lateinit var chatAdapter : ChatAdapter

    var chatList = ArrayList<ChatItem>()

    var ref : DatabaseReference? = null
    var query : Query? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        /*setSupportActionBar(main_layout_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        rv_chat.setHasFixedSize(true)
        rv_chat.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        chatAdapter = ChatAdapter(this,chatList)
        rv_chat.adapter = chatAdapter

        //chatViewModel=ViewModelProvider(this).get(ChatViewModel::class.java)
        chatViewModel= ChatViewModel(application)

        chatViewModel.getAllElement().observe(this, Observer {
            chatList.clear()
            it.forEach {
                chatList.add(ChatItem(it))
            }
            chatAdapter.notifyDataSetChanged()
        })

        btn_send.setOnClickListener {
            if(edit_chat.text.toString()=="") return@setOnClickListener

            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var current = simpleDateFormat.format(System.currentTimeMillis())

            Retrofit.insertChat("test_room_id","test","test",edit_chat.text.toString(),current,1,0){
                if(it.result == "success"){
                    writeFirebase("test_room_id","test","test",edit_chat.text.toString(),current, 1, 0)
                    chatViewModel.insert(Chat(0,"test_room_id","test","test",edit_chat.text.toString(),current,1,0)){
                        Log.d("test",chatList.toString())
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ref = FirebaseDatabase.getInstance().reference.child("chat").child("test_room_id")
        query = ref!!.orderByChild("chat_time")

        query!!.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if(ref != null) chatChange(snapshot)
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(ref != null) chatAdd(snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        ref=null
        query=null
    }

    fun writeFirebase(roomId: String, userId: String, userNickname: String, chatContent: String, chatTime: String, unreadCount: Int, systemChat: Int){

        var map = HashMap<String, Any>()
        val key: String? = ref!!.push().key

        var root = ref!!.child(key!!)
        var objectMap = HashMap<String, Any>()

        objectMap.put("room_id", roomId)
        objectMap.put("user_id", userId)
        objectMap.put("user_nickname", userNickname)
        objectMap.put("chat_content", chatContent)
        objectMap.put("chat_time", chatTime)
        objectMap.put("unread_count", unreadCount)
        objectMap.put("system_chat", systemChat)

        root.updateChildren(objectMap)
    }

    fun chatAdd(snapshot: DataSnapshot){
        var key=snapshot.key
        var value=snapshot.value as HashMap<String,Any>

        if("test"!=value.get("user_id").toString()){
            val hashMap=HashMap<String,Any>()
            hashMap.put("${key}/unread_count",0)
            ref!!.updateChildren(hashMap)
        }
        var i = snapshot.children.iterator()
        while (i.hasNext()) {
            Log.d("test",i.next().toString())


        }
    }

    fun chatChange(snapshot: DataSnapshot){
        var i = snapshot.children.iterator()
        while (i.hasNext()) {


        }
    }
}