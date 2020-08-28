package com.uniting.android.Room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_room_list.*

class RoomListActivity : AppCompatActivity() {

    var roomList = ArrayList<RoomItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        rv_open.setHasFixedSize(true)
        rv_open.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rv_open.adapter=RoomAdapter(this,roomList)

    }
}