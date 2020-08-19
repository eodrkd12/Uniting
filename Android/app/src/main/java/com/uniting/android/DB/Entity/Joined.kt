package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joined")
data class Joined (
    @PrimaryKey
    val room_id : String,
    @PrimaryKey
    val user_id : String,
    val enter_date : String,
    val alarm_chat : Int
)