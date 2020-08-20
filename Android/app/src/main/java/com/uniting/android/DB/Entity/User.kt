package com.uniting.android.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey
    val user_id : String,
    val user_pw : String,
    val user_nickname : String,
    val user_birthday : String,
    val user_gender : String,
    val user_email : String,
    val univ_name : String,
    val dept_name : String,
    val enter_year : String,
    val user_city : String,
    val user_hobby : String,
    val user_personality : String,
    val user_previewintroduce : String,
    val user_introduce : String,
    val token : String,
    val blocking_dept : Int,
    val alarm_visit : Int,
    val alarm_openprofile : Int
)