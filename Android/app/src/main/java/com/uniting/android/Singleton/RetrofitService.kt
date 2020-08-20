package com.uniting.android.Singleton

import com.uniting.android.Item.Test
import retrofit2.Call
import retrofit2.http.GET
import java.sql.Array

interface RetrofitService {
    @GET("/user/data")
    fun getData() : Call<ArrayList<Test.User>>
}