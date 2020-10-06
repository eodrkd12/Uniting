package com.uniting.android.Cafeteria

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CafeteriaItem {
    data class CafeteriaList(
        @SerializedName("items")
        val cafeteriaList : ArrayList<Cafeteria>
    )

    data class Cafeteria(
        @SerializedName("title")
        val title : String,
        @SerializedName("link")
        val link : String,
        @SerializedName("description")
        val description : String,
        @SerializedName("telephone")
        val telephone : String,
        @SerializedName("address")
        val address : String,
        @SerializedName("roadAddress")
        val roadAddress : String,
        @SerializedName("mapx")
        val mapx : Int,
        @SerializedName("mapy")
        val mapy : Int,
        var starPoint:String
    )

    data class Menu (
        val name:String?,
        val price:String?
    ) : Serializable

    data class Review(
        @SerializedName("review_id")
        val reviewId: Int,
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