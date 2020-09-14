package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "room")
data class Room(
    @PrimaryKey
    @SerializedName("room_id")
    val room_id : String,
    @SerializedName("room_title")
    val room_title : String,
    @SerializedName("maker")
    val maker : String,
    @SerializedName("category")
    val category : String,
    @SerializedName("room_date")
    val room_date : String,
    @SerializedName("room_introduce")
    val room_introduce : String,
    @SerializedName("univ_name")
    val univ_name : String
) : Serializable