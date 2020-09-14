package com.uniting.android.Room

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.UserInfo
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

        Retrofit.getOpenChatList(UserInfo.UNIV,category){
            if(it.size == 0) {
                text_no_open_chat.visibility = View.VISIBLE
                btn_make_open_chat.visibility = View.VISIBLE
                btn_make_open_chat.setOnClickListener {
                    var intent = Intent(this, MakeRoomActivity::class.java)
                    startActivity(intent)
                }
            }
            else {
                rv_open.setHasFixedSize(true)
                rv_open.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                rv_open.adapter=RoomAdapter(this,roomList)

                it.forEach {
                    var room = it
                    var numOfMembers = 0
                    Retrofit.getMembers(it.room_id){
                        numOfMembers = it.size
                        roomList.add(RoomItem(room,numOfMembers))
                        rv_open.adapter!!.notifyDataSetChanged()
                    }
                }

                val spaceDecoration = VerticalSpaceItemDecoration(20) // RecyclerView 간격
                rv_open.addItemDecoration(spaceDecoration)
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}