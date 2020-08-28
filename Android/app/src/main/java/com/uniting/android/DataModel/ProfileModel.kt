package com.uniting.android.DataModel

class ProfileModel {
    data class Profile(
        val userId : String,
        val userNickname : String,
        val userAge : String,
        val deptName : String,
        val enterYear : String,
        val userCity : String,
        val userHobby : String,
        val userPersonality : String,
        val userIntroduce: String,
        val userHeight: String
    )
}