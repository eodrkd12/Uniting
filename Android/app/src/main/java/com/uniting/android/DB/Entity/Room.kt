package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class Room(
    @PrimaryKey
    val room_id : String,
    val room_title : String,
    val maker : String,
    val category : String,
    val room_date : String,
    val room_introduce : String,
    val univ_name : String
)