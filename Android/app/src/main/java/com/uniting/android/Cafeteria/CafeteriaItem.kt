package com.uniting.android.Cafeteria

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CafeteriaItem {
    data class CafeteriaList(
        @SerializedName("cafe_name")
        val cafeteriaName : String,
        @SerializedName("cafe_type")
        val cafeteriaType : String,
        var starPoint : Int = 0
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