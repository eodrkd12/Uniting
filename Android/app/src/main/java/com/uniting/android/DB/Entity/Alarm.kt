package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    val alarm_id : Int,
    val user_id : String,
    val alarm_content : String,
    val alarm_date : String,
    val alarm_type : String,
    val alarm_check : Int
)