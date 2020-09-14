package com.uniting.android.Class

import android.app.Application

class UserInfo : Application() {
    companion object {
        var ID: String = "test"
        var PW: String = "test"
        var NICKNAME: String = "김세현짱"
        var BIRTHDAY : String = ""
        var GENDER: String = "F"
        var UNIV : String ="계명대학교"
        var DEPT : String = ""
        var BLOCKINGDEPT : Int = 0
    }
}