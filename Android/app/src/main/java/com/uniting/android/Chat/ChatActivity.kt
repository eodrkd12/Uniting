package com.uniting.android.Chat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.DataModel.MemberModel
import com.uniting.android.Login.LoginActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_mainchat.*

class ChatActivity : PSAppCompatActivity() {

    companion object{
        var chatRV : RecyclerView? = null
    }

    lateinit var room : Room
    lateinit var chatViewModel : ChatViewModel
    lateinit var chatAdapter : ChatAdapter
    lateinit var memberAdapter : MemberAdapter

    lateinit var memberList : ArrayList<MemberItem>


    var chatList = ArrayList<ChatItem>()

    var ref : DatabaseReference? = null
    var query : Query? = null

    var lastChatTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainchat)
        setSupportActionBar(toolbar_chat)

        chatRV = rv_chat

        memberList = ArrayList<MemberItem>()
        room = intent.getSerializableExtra("room") as Room
        lastChatTime = intent.getStringExtra("last_chat_time")!!

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = room.room_title

        FirebaseMessaging.getInstance().subscribeToTopic(room.room_id).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("test","구독")
            }
        }

        Retrofit.getMembers(room.room_id){
            memberList = ArrayList<MemberItem>()
            it.forEach {
                memberList.add(MemberItem(it))
            }


            //drawer layout
            text_me.text = UserInfo.NICKNAME

            rv_member.setHasFixedSize(true)
            rv_member.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            memberAdapter = MemberAdapter(this, memberList)
            rv_member.adapter = memberAdapter
            memberAdapter.notifyDataSetChanged()
        }

        rv_chat.setHasFixedSize(true)
        rv_chat.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        chatAdapter = ChatAdapter(this,chatList)
        rv_chat.adapter = chatAdapter

        chatViewModel= ChatViewModel(application, room.room_id)

        chatViewModel.getAllElement().observe(this, Observer {
            chatList.clear()
            it.forEach {
                chatList.add(ChatItem(it))
            }
            chatAdapter.notifyDataSetChanged()

        })

        btn_send.setOnClickListener {
            if(edit_chat.text.toString()=="") return@setOnClickListener
            Retrofit.getMembers(room.room_id){
                var content = edit_chat.text.toString()
                edit_chat.setText("")
                var date = this.getCurDate()

                var unreadMember = ""
                it.forEach {
                    unreadMember += "${it.id}|"
                }

                unreadMember = unreadMember.replace("${UserInfo.ID}|","")

                var chatId = "${UserInfo.ID}_${room.room_id}_${date}"

                var chat = Chat(chatId,room.room_id,UserInfo.ID,UserInfo.NICKNAME,content,date,unreadMember,0)

                Retrofit.insertChat(chat){
                    if(it.result == "success"){

                        writeFirebase(chat)

                        var topic = room.room_id
                        var title = room.room_title
                        var content = "${UserInfo.NICKNAME} ${edit_chat.text}"

                        Retrofit.sendFcm(topic, title, content)

                        chatViewModel.insert(chat){
                            rv_chat.scrollToPosition(chatAdapter.itemCount-1)
                        }
                    }
                }
            }
        }

        Handler().postDelayed({
            rv_chat.scrollToPosition(chatAdapter.itemCount-1)
        },50)
    }

    override fun onResume() {
        super.onResume()
        ref = FirebaseDatabase.getInstance().reference.child("chat").child(room.room_id)
        query = ref!!.orderByChild("chat_time").startAt(lastChatTime)

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

    fun writeFirebase(chat: Chat){

        var map = HashMap<String, Any>()
        val key: String? = ref!!.push().key

        var root = ref!!.child(key!!)
        var objectMap = HashMap<String, Any>()

        objectMap.put("chat_id", chat.chat_id)
        objectMap.put("room_id", chat.room_id)
        objectMap.put("user_id", chat.user_id)
        objectMap.put("user_nickname", chat.user_nickname)
        objectMap.put("chat_content", chat.chat_content)
        objectMap.put("chat_time", chat.chat_time)
        objectMap.put("unread_member", chat.unread_member)
        objectMap.put("system_chat", chat.system_chat)

        root.updateChildren(objectMap)
    }

    fun chatAdd(snapshot: DataSnapshot){
        var key=snapshot.key
        var value=snapshot.value as HashMap<String,Any>

        if(value.get("user_id").toString() != UserInfo.ID) {
            val hashMap = HashMap<String, Any>()

            var unreadMember = value.get("unread_member") as String

            unreadMember = unreadMember.replace("${UserInfo.ID}|", "")

            hashMap.put("${key}/unread_member", unreadMember)
            ref!!.updateChildren(hashMap)
        }
        var i = snapshot.children.iterator()
        var chat : Chat? = null
        while (i.hasNext()) {
            var chatContent = i.next().value as String
            var chatId = i.next().value as String
            var chatTime = i.next().value as String
            var roomId = i.next().value as String
            var systemChat = (i.next().value as Long).toInt()
            var unreadMember = i.next().value as String
            var userId = i.next().value as String
            var userNickname = i.next().value as String

            chat = Chat(chatId,roomId,userId,userNickname,chatContent,chatTime,unreadMember,systemChat)
        }
        chatViewModel.insert(chat!!){
        }
    }

    fun chatChange(snapshot: DataSnapshot){
        var i = snapshot.children.iterator()
        var chat : Chat? = null
        while (i.hasNext()) {
            var chatContent = i.next().value as String
            var chatId = i.next().value as String
            var chatTime = i.next().value as String
            var roomId = i.next().value as String
            var systemChat = (i.next().value as Long).toInt()
            var unreadMember = i.next().value as String
            var userId = i.next().value as String
            var userNickname = i.next().value as String

            chat = Chat(chatId,roomId,userId,userNickname,chatContent,chatTime,unreadMember,systemChat)
        }
        chatViewModel.insert(chat!!){
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_chat, menu)
        menu!!.add(0,0,0,"메뉴")
            .setIcon(R.drawable.option1_icon)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return super.onCreateOptionsMenu(menu)
    }

    private val navListener = NavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
        }
        false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            0-> { // 메뉴 버튼
                drawerlayout_chat.openDrawer(GravityCompat.END)
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return false
    }
    override fun onBackPressed() {
        if (drawerlayout_chat.isDrawerOpen(GravityCompat.END)) {
            drawerlayout_chat.closeDrawers()
        }
        else{
            super.onBackPressed()
        }
    }
}