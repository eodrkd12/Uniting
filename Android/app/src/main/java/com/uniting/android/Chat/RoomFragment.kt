package com.uniting.android.Chat

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
import androidx.room.Room
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.DB.ViewModel.JoinedViewModel
import com.uniting.android.DB.ViewModel.RoomViewModel
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class RoomFragment : Fragment() {

    lateinit var roomViewModel : RoomViewModel
    lateinit var chatViewModel : ChatViewModel
    lateinit var joinedViewModel : JoinedViewModel

    lateinit var roomAdapter : RoomAdapter

    var roomList = ArrayList<RoomItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_chat, container, false)

        var rvRoom=rootView.findViewById<RecyclerView>(R.id.rv_room)
        var textNothingRoom=rootView.findViewById<TextView>(R.id.text_nothingroom)

        rvRoom.setHasFixedSize(true)
        rvRoom.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        roomViewModel= RoomViewModel(activity!!.application)
        chatViewModel= ChatViewModel(activity!!.application)
        joinedViewModel= JoinedViewModel(activity!!.application)

        roomViewModel.getAllElement().observe(this, Observer {
            roomList.clear()
            it.forEach{
                var lastChat = ""
                var lastChatTime = ""
                var numOfMembers = 0

                chatViewModel.getLastChat(it.room_id).observe(this, Observer{
                    lastChat=it[0].chat_content
                    lastChatTime=it[0].chat_time
                })

                numOfMembers = joinedViewModel.getNumOfMembers(it.room_id)

                roomList.add(
                    RoomItem(
                        it,
                        lastChat,
                        lastChatTime,
                        numOfMembers
                    )
                )
            }
        })

        Retrofit.getMyRoom("test"){
            Log.d("test",it.toString())
        }

        return rootView
    }
}