package com.uniting.android.Cafeteria

import com.google.gson.annotations.SerializedName

class CafeteriaItem {
    data class CafeteriaList(
        @SerializedName("items")
        val cafeteriaList : ArrayList<Cafeteria>
    )

    data class Cafeteria(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("x")
        val x: String,
        @SerializedName("y")
        val y: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("imageSrc")
        val imageSrc: String,
        @SerializedName("roadAddr")
        val roadAddr: String,
        @SerializedName("tags")
        val tags: ArrayList<String>,
        @SerializedName("options")
        val options: String,
        @SerializedName("bizHourInfo")
        val bizHourInfo: String,
        var starPoint:String
    )


    data class User(
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_pw")
        val userPw: String
    )
}