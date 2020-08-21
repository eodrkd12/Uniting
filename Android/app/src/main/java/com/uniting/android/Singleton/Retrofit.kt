package com.uniting.android.Singleton

import com.uniting.android.Interface.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://52.78.27.41:1901")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(RetrofitService::class.java)
}