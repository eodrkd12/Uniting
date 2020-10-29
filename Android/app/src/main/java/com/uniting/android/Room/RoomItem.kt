package com.uniting.android.Room

import com.google.gson.annotations.SerializedName
import com.uniting.android.DB.Entity.Room
import java.io.Serializable

data class RoomItem(
    var room: Room,
    var numOfMembers : Int
) : Serializable