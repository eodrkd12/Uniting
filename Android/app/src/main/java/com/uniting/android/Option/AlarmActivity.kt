package com.uniting.android.Option

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.firebase.messaging.FirebaseMessaging
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DB.ViewModel.RoomViewModel
import com.uniting.android.R
import com.uniting.android.Room.MyRoomItem
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        btn_alarm_back.setOnClickListener {
            finish()
        }

        var roomViewModel = RoomViewModel(application)
        var roomList = listOf<Room>()
        roomViewModel.getAllElement().observe(this, Observer {
            roomList = it
        })

        switch_alarm_message.setOnCheckedChangeListener { compoundButton, b ->
            when(b){
                true -> {
                    roomList.forEach {
                        FirebaseMessaging.getInstance().subscribeToTopic(it.room_id)
                    }
                    Retrofit.allChatAlarmOn(UserInfo.ID)
                }
                false -> {
                    roomList.forEach {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(it.room_id)
                    }
                    Retrofit.allChatAlarmOff(UserInfo.ID)
                }
            }
        }
    }
}