package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("chat_id")
    val chat_id : Int,
    @SerializedName("room_id")
    val room_id : String,
    @SerializedName("user_id")
    val user_id : String,
    @SerializedName("user_nickname")
    val user_nickname : String,
    @SerializedName("chat_content")
    val chat_content : String,
    @SerializedName("chat_time")
    val chat_time : String,
    @SerializedName("unread_count")
    val unread_count : Int,
    @SerializedName("system_chat")
    val system_chat : Int
)