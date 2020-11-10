package com.uniting.android.Room

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.uniting.android.DB.Entity.Room

data class MyRoomItem (
    val room_id : String,
    val room_title : String,
    val maker : String,
    val category : String,
    val room_date : String,
    val room_introduce : String,
    val univ_name : String,
    var chat_content : String?,
    var chat_time : String?
)