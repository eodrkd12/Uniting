package com.uniting.android.Interface

import com.uniting.android.Cafeteria.CafeteriaItem
import com.uniting.android.Chat.ChatItem
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DataModel.CountModel
import com.uniting.android.DataModel.IdModel
import com.uniting.android.DataModel.ProfileModel
import com.uniting.android.DataModel.ResultModel
import com.uniting.android.Item.Test
import com.uniting.android.Login.UniversityItem
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("/user/data")
    fun getData(): Call<ArrayList<Test.User>>

    @FormUrlEncoded
    @POST("/")
    fun getChat(@Field("sql") sql: String): Call<ArrayList<Chat>>

    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun insert(@Field("sql") sql: String): Call<ResultModel>

    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getMyRoom(@Field("sql") sql: String): Call<ArrayList<Room>>

    //리뷰 작성(이미지 포함)
    @Multipart
    @POST("/image/review/insert")
    fun insertReview(
        @Part("user_id") userId: String,
        @Part("user_nickname") userNickname: String,
        @Part("cafe_name") cafeteriaName: String,
        @Part("review_content") reviewContent: String,
        @Part("review_date") reviewDate: String,
        @Part("review_point") reviewPoint: Int,
        @Part file: MultipartBody.Part?
    ): Call<ResultModel>

    /* RequestBody 타입으로 보내면 큰따옴표 생략됨
    예제)
    val userId= "123456";
    var id : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, userId);
    */

    //리뷰 작성(이미지 제외)
    @FormUrlEncoded
    @POST("/image/review/insert/noimage")
    fun insertNoImageReview(
        @Field("user_id") userId: String,
        @Field("user_nickname") userNickname: String,
        @Field("cafe_name") cafeteriaName: String,
        @Field("review_content") reviewContent: String,
        @Field("review_date") reviewDate: String,
        @Field("review_point") reviewPoint: Int,
        @Field("review_type") reviewType: String
    ): Call<ResultModel>

    //방 만들기
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun createRoom(@Field("sql") sql: String): Call<ResultModel>

    //방 참가
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun joinRoom(@Field("sql") sql: String): Call<ResultModel>

    //리뷰 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getReview(@Field("sql") sql: String): Call<ArrayList<CafeteriaItem.Review>>

    //프로필 정보 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun randomMatching(@Field("sql") sql: String): Call<ArrayList<ProfileModel.Profile>>

    //리뷰삭제
    @FormUrlEncoded
    @POST("/image/review/delete")
    fun deleteReview(
        @Field("review_id") reviewId: Int,
        @Field("image_path") imagePath: String
    ): Call<ResultModel>

    //대학 리스트 불러오기
    @GET("/university")
    fun getUniversity(): Call<ArrayList<UniversityItem.University>>

    //학과 리스트 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getDepartment(@Field("sql") sql: String): Call<ArrayList<UniversityItem.Department>>

    //아이디 중복확인
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun idCheck(@Field("sql") sql: String): Call<CountModel>

    //임시 아이디 데이터 삽입
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun idInsert(@Field("sql") sql: String): Call<ResultModel>

    //회원삭제 & 임시데이터 삭제
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun idDelete(@Field("sql") sql: String): Call<ResultModel>

    //회원가입
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun signUp(@Field("sql") sql: String): Call<ResultModel>

    //방 인원 수 가져오기
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getMembers(@Field("sql") sql: String): Call<ArrayList<IdModel>>

    //오픈채팅 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getOpenChatList(@Field("sql") sql: String): Call<ArrayList<Room>>
}