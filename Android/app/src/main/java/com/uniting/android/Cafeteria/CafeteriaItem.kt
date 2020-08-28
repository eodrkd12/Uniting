package com.uniting.android.Cafeteria

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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

    data class Menu (
        val name:String?,
        val price:String?
    ) : Serializable

    data class Review(
        @SerializedName("user_id")
        val userId : String,
        @SerializedName("user_nickname")
        val userNickname : String,
        @SerializedName("review_content")
        val reviewContent : String,
        @SerializedName("review_date")
        val reviewDate : String,
        @SerializedName("review_point")
        val reviewPoint : Int,
        @SerializedName("image_url")
        val imageUrl : String?
    ) : Serializable
}