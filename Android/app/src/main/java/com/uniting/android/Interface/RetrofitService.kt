package com.uniting.android.Interface

import com.uniting.android.Chat.ChatItem
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.Item.Test
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("/user/data")
    fun getData() : Call<ArrayList<Test.User>>

    @POST("/")
    fun getChat(@Field("sql") sql: String) : Call<ArrayList<Chat>>
    @POST("/chat/sql")
    fun insertChat(@Field("sql") sql: String) : Call<String>
}