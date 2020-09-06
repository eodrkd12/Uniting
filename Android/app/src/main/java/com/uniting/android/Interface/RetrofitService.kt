package com.uniting.android.Interface

import com.uniting.android.Chat.ChatItem
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DataModel.ResultModel
import com.uniting.android.Item.Test
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("/user/data")
    fun getData() : Call<ArrayList<Test.User>>

    @FormUrlEncoded
    @POST("/")
    fun getChat(@Field("sql") sql: String) : Call<ArrayList<Chat>>

    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun insert(@Field("sql") sql: String) : Call<ResultModel>

    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getMyRoom(@Field("sql") sql: String) : Call<ArrayList<Room>>

    @FormUrlEncoded
    @POST("/review/insert")
    fun insertReview(@Field("user_id") userId: String,
    @Field("user_nickname") userNickname: String,
    @Field("cafe_name") cafeteriaName : String,
    @Field("review_content") reviewContent: String,
    @Field("review_date") reviewDate: String,
    @Field("review_point") reviewPoint: Int,
    @Field("review_type") reviewType: String) : Call<ResultModel>

    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun createRoom(@Field("sql") sql: String) : Call<ResultModel>
}