package com.uniting.android.Class

import android.app.Application

class UserInfo : Application() {
    companion object {
        var ID: String = "ljs"
        var PW: String = "test"
        var NICKNAME: String = "test"
        var BIRTHDAY : String = ""
        var GENDER: String = "M"
        var DEPT : String = ""
        var BLOCKINGDEPT : Int = 0
    }
}