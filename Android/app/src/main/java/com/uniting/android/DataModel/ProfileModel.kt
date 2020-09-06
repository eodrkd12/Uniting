package com.uniting.android.DataModel

import com.google.gson.annotations.SerializedName

class ProfileModel {
    data class Profile(
        @SerializedName("user_id")
        val userId : String,
        @SerializedName("user_nickname")
        val userNickname : String,
        @SerializedName("user_birthday")
        val userAge : String,
        @SerializedName("dept_name")
        val deptName : String,
        @SerializedName("user_gender")
        val userGender: String,
        @SerializedName("enter_year")
        val enterYear : String,
        @SerializedName("user_city")
        val userCity : String,
        @SerializedName("user_hobby")
        val userHobby : String,
        @SerializedName("user_personality")
        val userPersonality : String,
        @SerializedName("user_introduce")
        val userIntroduce: String,
        @SerializedName("user_height")
        val userHeight: String
    )
}