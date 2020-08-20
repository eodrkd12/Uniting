package com.uniting.android.Item

import com.google.gson.annotations.SerializedName

class Test {
    data class User(
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_pw")
        val userPw: String
    )
}