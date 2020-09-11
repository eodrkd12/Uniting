package com.uniting.android.Login

import com.google.gson.annotations.SerializedName

class UserItem {
    data class UserOption(
        val title: String,
        var isSelected : Boolean = false
    )

    data class ModifyUser(
        @SerializedName("user_nickname")
        val userNickname : String,
        @SerializedName("user_birthday")
        val userBirthday : String,
        @SerializedName("user_gender")
        val userGender : String,
        @SerializedName("user_email")
        val userEmail : String,
        @SerializedName("univ_name")
        val univName : String,
        @SerializedName("dept_name")
        val deptName : String,
        @SerializedName("enter_year")
        val enterYear : String,
        @SerializedName("user_city")
        val userCity : String,
        @SerializedName("user_signdate")
        val user_signdate : String
    )
}