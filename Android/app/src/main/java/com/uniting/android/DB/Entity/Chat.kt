package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("chat_id")
    val chat_id : Int,
    val room_id : String,
    val user_id : String,
    val user_nickname : String,
    val chat_content : String,
    val chat_time : String,
    val unread_count : Int,
    val system_chat : Int
)