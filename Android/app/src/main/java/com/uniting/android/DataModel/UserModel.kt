package com.uniting.android.DataModel

import com.google.gson.annotations.SerializedName

class UserModel {
    data class User(
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_pw")
        val userPw: String,
        @SerializedName("user_nickname")
        val userNickname : String,
        @SerializedName("user_gender")
        val userGender : String,
        @SerializedName("univ_name")
        val univName : String,
        @SerializedName("dept_name")
        val deptName : String,
        @SerializedName("blocking_dept")
        val blockingDept : Int,
        @SerializedName("matching_age")
        val matchingAge : String,
        @SerializedName("matching_dept")
        val matchingDept : String,
        @SerializedName("matching_height")
        val matchingHeight : String,
        @SerializedName("matching_hobby")
        val matchingHobby : String,
        @SerializedName("matching_personality")
        val matchingPersonality : String,
        @SerializedName("result")
        val result : String
    )
}