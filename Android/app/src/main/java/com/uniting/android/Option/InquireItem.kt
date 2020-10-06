package com.uniting.android.Option

import com.google.gson.annotations.SerializedName

class InquireItem {
    data class Inquire(
        @SerializedName("inquire_date")
        val inquireDate : String,
        @SerializedName("inquire_title")
        val inquireTitle : String,
        @SerializedName("inquire_content")
        val inquireContent : String,
        @SerializedName("inquire_answer")
        val inquireAnswer : String,
        @SerializedName("inquire_type")
        val inquireType: String,
        @SerializedName("inquire_check")
        val inquireCheck: Int,
        @SerializedName("answer_date")
        val answerDate : String
    )
}