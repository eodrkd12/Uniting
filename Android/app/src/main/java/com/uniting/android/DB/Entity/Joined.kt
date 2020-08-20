package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joined", primaryKeys = arrayOf("room_id","user_id"))
data class Joined (
    val room_id : String,
    val user_id : String,
    val enter_date : String,
    val alarm_chat : Int
)