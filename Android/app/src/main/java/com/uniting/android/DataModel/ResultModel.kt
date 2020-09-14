package com.uniting.android.DataModel

import com.google.gson.annotations.SerializedName

data class ResultModel (
    @SerializedName("result")
    var result : String
)

data class CountModel (
    @SerializedName("count")
    var count : Int
)

data class IdModel (
    @SerializedName("user_id")
    var id : String
)