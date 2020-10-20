package com.uniting.android.Cafeteria

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CafeteriaItem {
    data class CafeteriaList(
        @SerializedName("koreanFood")
        val koreanFoodList : ArrayList<CafeteriaPreview>,
        @SerializedName("japaneseFood")
        val japaneseFoodList : ArrayList<CafeteriaPreview>,
        @SerializedName("chineseFood")
        val chineseFoodList : ArrayList<CafeteriaPreview>,
        @SerializedName("westernFood")
        val westernFoodList : ArrayList<CafeteriaPreview>,
        @SerializedName("chickenFood")
        val chickenFoodList : ArrayList<CafeteriaPreview>
    )

    data class CafeteriaPreview(
        @SerializedName("cafe_no")
        val cafeNo : Int,
        @SerializedName("cafe_name")
        val cafeteriaName : String,
        @SerializedName("star_point")
        val starPoint : Double
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