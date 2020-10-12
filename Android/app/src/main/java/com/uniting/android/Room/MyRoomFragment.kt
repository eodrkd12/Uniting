package com.uniting.android.Room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.DB.ViewModel.JoinedViewModel
import com.uniting.android.DB.ViewModel.RoomViewModel
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class MyRoomFragment : Fragment() {

    lateinit var roomViewModel : RoomViewModel
    lateinit var chatViewModel : ChatViewModel
    lateinit var joinedViewModel : JoinedViewModel

    lateinit var roomAdapter : MyRoomAdapter

    var roomList = ArrayList<MyRoomItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_myroom, container, false)

        var rvRoom=rootView.findViewById<RecyclerView>(R.id.rv_room)
        var textNothingRoom=rootView.findViewById<TextView>(R.id.text_nothingroom)

        roomAdapter = MyRoomAdapter(activity!!, roomList)

        rvRoom.setHasFixedSize(true)
        rvRoom.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvRoom.adapter = roomAdapter

        roomViewModel= RoomViewModel(activity!!.application)
        //chatViewModel= ChatViewModel(activity!!.application,"")
        //joinedViewModel= JoinedViewModel(activity!!.application)

        roomViewModel.getAllElement().observe(this, Observer {
            roomList.clear()
            it.forEach{
                roomList.add(
                    MyRoomItem(
                        it,"",""
                    )
                )
            }
            roomAdapter.sortByLastChat()
            roomAdapter.notifyDataSetChanged()
        })

        Retrofit.getMyRoom(UserInfo.ID){
            roomList.clear()
            it.forEach{
                roomViewModel.insert(it){
                }
            }
        }

        return rootView
    }
}