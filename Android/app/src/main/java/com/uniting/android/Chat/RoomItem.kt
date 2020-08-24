package com.uniting.android.Chat

import com.uniting.android.DB.Entity.Room

data class RoomItem (
    var room : Room,
    var lastChat : String,
    var lastChatTime : String,
    var numOfMembers : Int
)