package com.uniting.android.Room

import com.google.gson.annotations.SerializedName

data class RoomItem (
    @SerializedName("room_id")
    var roomId : String,
    @SerializedName("room_title")
    var roomTitle : String,
    @SerializedName("category")
    var category : String,
    @SerializedName("room_date")
    var roomDate : String,
    @SerializedName("room_introduce")
    var roomIntroduce : String,
    @SerializedName("univ_name")
    var univName : String,
    @SerializedName("maker")
    var maker: String,
    @SerializedName("num_of_members")
    var numOfMembers : Int
)