package com.uniting.android.Class

import android.app.Application

class UserInfo : Application() {
    companion object {
        var ID: String = ""
        var PW: String = ""
        var NICKNAME: String = ""
        var BIRTHDAY : String = ""
        var GENDER: String = ""
        var UNIV : String =""
        var DEPT : String = ""
        var BLOCKINGDEPT : Int = 0
    }
}