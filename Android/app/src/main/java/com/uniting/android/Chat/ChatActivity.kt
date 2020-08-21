package com.uniting.android.Chat

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class ChatActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var query : Query

    lateinit var chatViewModel : ChatViewModel

    lateinit var chatAdapter : ChatAdapter

    var chatList = ArrayList<ChatItem>()

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
            chatViewModel.insert(Chat(0,"1","test","test",edit_chat.text.toString(),"asd",1,0)){
            }
        }
    }
}