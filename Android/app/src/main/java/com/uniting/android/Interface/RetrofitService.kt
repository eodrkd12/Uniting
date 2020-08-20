package com.uniting.android.Interface

import com.uniting.android.Item.Test
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("/user/data")
    fun getData() : Call<ArrayList<Test.User>>
}