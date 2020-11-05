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
        @SerializedName("fastFood")
        val fastFoodList : ArrayList<CafeteriaPreview>
    )

    data class CafeteriaPreview(
        @SerializedName("cafe_no")
        val cafeNo : Int,
        @SerializedName("cafe_name")
        val cafeteriaName : String,
        @SerializedName("star_point")
        val starPoint : Double,
        @SerializedName("cafe_thumbnail")
        val cafeThumbnail : String
    )

    data class Cafeteria(
        @SerializedName("inform")
        val inform : CafeteriaInform,
        @SerializedName("menu")
        val menuList : ArrayList<Menu>,
        @SerializedName("review")
        val reviewList : ArrayList<Review>
    )

    data class CafeteriaInform(
        @SerializedName("cafe_address")
        val cafeAddress : String,
        @SerializedName("cafe_name")
        val cafeName : String,
        @SerializedName("cafe_phone")
        val cafePhone : String,
        @SerializedName("cafe_menu")
        val cafeMenu : String,
        @SerializedName("cafe_bizhour")
        val cafeBizHour : String,
        @SerializedName("cafe_mapx")
        val mapx : Double,
        @SerializedName("cafe_mapy")
        val mapy : Double
    )

    data class Menu (
        @SerializedName("menu_title")
        val menuTitle:String?,
        @SerializedName("menu_price")
        val menuPrice:String?
    ) : Serializable

    data class Review(
        @SerializedName("review_no")
        val reviewNo : Int,
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
        val imageUrl : String
    ) : Serializable
}