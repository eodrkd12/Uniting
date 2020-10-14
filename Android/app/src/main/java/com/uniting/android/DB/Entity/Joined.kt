package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "joined", primaryKeys = arrayOf("room_id","user_id"))
data class Joined (
    @SerializedName("room_id")
    val room_id : String,
    @SerializedName("user_id")
    val user_id : String,
    @SerializedName("enter_date")
    val enter_date : String,
    @SerializedName("alarm_chat")
    val alarm_chat : Int
)