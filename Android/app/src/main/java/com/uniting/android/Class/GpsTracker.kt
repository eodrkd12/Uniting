package com.uniting.android.Class

import android.app.Activity

class GpsTracker(activity: Activity) {
    enum class TimeValue(val value: Int,val maximum : Int, val msg : String) {
        SEC(60,60,"분전"),
        MIN(60,24,"시간전"),
        HOUR(24,30,"일전"),
        DAY(30,12,"달전"),
        MONTH(12,Int.MAX_VALUE,"년전")
    }

    fun timeDiff(time : Long):String{
        val curTime = System.currentTimeMillis()
        var diffTime = (curTime- time) / 1000
        var msg: String? = null
        if(diffTime < TimeValue.SEC.value)
            msg= "방금 전"
        else {
            for (i in TimeValue.values()) {
                diffTime /= i.value
                if (diffTime < i.maximum) {
                    msg= diffTime.toString() + i.msg
                    break
                }
            }
        }
        return msg!!
    }
}