package com.uniting.android.Interface

import com.uniting.android.Cafeteria.CafeteriaItem
import com.uniting.android.Chat.ChatItem
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DataModel.ProfileModel
import com.uniting.android.DataModel.ResultModel
import com.uniting.android.Item.Test
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("/user/data")
    fun getData() : Call<ArrayList<Test.User>>

    @FormUrlEncoded
    @POST("/common/sql")
    fun insert(@Field("sql") sql: String) : Call<ResultModel>

    @FormUrlEncoded
    @POST("/common/sql")
    fun getMyRoom(@Field("sql") sql: String) : Call<ArrayList<Room>>

    @Multipart
    @POST("/image/review/insert")
    fun insertReview(@Part("user_id") userId: String,
    @Part("user_nickname") userNickname: String,
    @Part("cafe_name") cafeteriaName : String,
    @Part("review_content") reviewContent: String,
    @Part("review_date") reviewDate: String,
    @Part("review_point") reviewPoint: Int,
    @Part file: MultipartBody.Part?) : Call<ResultModel>

    /* RequestBody 타입으로 보내면 큰따옴표 생략됨
    예제)
    val userId= "123456";
    var id : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, userId);
    */

    @FormUrlEncoded
    @POST("/image/review/insert/noimage")
    fun insertNoImageReview(@Field("user_id") userId: String,
    @Field("user_nickname") userNickname : String,
    @Field("cafe_name") cafeteriaName: String,
    @Field("review_content") reviewContent: String,
    @Field("review_date") reviewDate: String,
    @Field("review_point") reviewPoint: Int) : Call<ResultModel>

    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getReview(@Field("sql") sql : String) : Call<ArrayList<CafeteriaItem.Review>>

    @FormUrlEncoded
    @POST("/common/sql/select")
    fun randomMatching(@Field("sql") sql : String) : Call<ArrayList<ProfileModel.Profile>>
}