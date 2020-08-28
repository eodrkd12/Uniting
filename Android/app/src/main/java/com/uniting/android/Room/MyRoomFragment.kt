package com.uniting.android.Room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        rvRoom.setHasFixedSize(true)
        rvRoom.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        roomViewModel= RoomViewModel(activity!!.application)
        chatViewModel= ChatViewModel(activity!!.application)
        joinedViewModel= JoinedViewModel(activity!!.application)

        roomViewModel.getAllElement().observe(this, Observer {
            roomList.clear()
            it.forEach{


                roomList.add(
                    MyRoomItem(
                        it
                    )
                )
            }
        })

        Retrofit.getMyRoom("test"){
            roomList.clear()
            it.forEach{
            }
        }

        return rootView
    }
}