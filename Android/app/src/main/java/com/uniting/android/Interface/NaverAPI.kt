package com.uniting.android.Interface

import com.uniting.android.Cafeteria.CafeteriaItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NaverAPI {
    @GET("local.json")
    fun getCafeteriaList(
        @Query("start") start: Int,
        @Query("display") display: Int,
        @Query("query") query: String,
        @Header("X-Naver-Client-Id") clientId : String,
        @Header("X-Naver-Client-Secret") clientSecret : String
    ) : Call<CafeteriaItem.CafeteriaList>

}