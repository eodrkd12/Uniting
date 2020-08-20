package com.uniting.android.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.R
import kotlinx.android.synthetic.main.toolbar_layout.*

class ChatActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var query : Query

    lateinit var chatViewModel : ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        /*setSupportActionBar(main_layout_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        chatViewModel=ViewModelProvider(this).get(ChatViewModel::class.java)


    }
}