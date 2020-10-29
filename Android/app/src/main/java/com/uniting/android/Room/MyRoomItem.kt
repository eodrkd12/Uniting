package com.uniting.android.Room

import com.uniting.android.DB.Entity.Room

data class MyRoomItem (
    var room : Room,
    var lastChatTime : String,
    var lastChat : String
)