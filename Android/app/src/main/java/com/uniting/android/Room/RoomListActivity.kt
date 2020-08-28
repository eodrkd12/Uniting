package com.uniting.android.Room

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_room_list.*

class RoomListActivity : PSAppCompatActivity() {

    var roomList = ArrayList<RoomItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        var intent=intent
        var category = intent.getStringExtra("category")

        text_category.setText(category)

        Retrofit.getOpenChatList("test",category){

        }

        rv_open.setHasFixedSize(true)
        rv_open.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rv_open.adapter=RoomAdapter(this,roomList)

        val spaceDecoration = VerticalSpaceItemDecoration(20) // RecyclerView 간격

        rv_open.addItemDecoration(spaceDecoration)

        btn_back.setOnClickListener {
            finish()
        }
    }
}