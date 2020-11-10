package com.uniting.android.Interface

import com.uniting.android.Cafeteria.CafeteriaItem
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Joined
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DataModel.*
import com.uniting.android.Item.Test
import com.uniting.android.Login.UniversityItem
import com.uniting.android.Login.UserItem
import com.uniting.android.Option.InquireItem
import okhttp3.MultipartBody
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
        @Part("cafe_no") cafeNo : Int,
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
        @Field("cafe_no") cafeNo : Int,
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


    //스마트 매칭
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun smartMatching(@Field("sql") sql: String): Call<ArrayList<ProfileModel.Profile>>

    //리뷰삭제
    @FormUrlEncoded
    @POST("/image/review/delete")
    fun deleteReview(
        @Field("review_no") reviewNo: Int,
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
    @POST("/common/sql/select/single")
    fun idCheck(@Field("sql") sql: String) : Call<CountModel>

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
    fun getMembers(@Field("sql") sql: String): Call<ArrayList<MemberModel>>

    //오픈채팅 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getOpenChatList(@Field("sql") sql: String): Call<ArrayList<Room>>

    //중복 회원가입 확인
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun accountCheck(@Field("sql") sql : String) : Call<CountModel>

    //로그인
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("user_id") userId : String,
    @Field("user_pw") userPw : String) : Call<UserModel.User>

    //회원정보수정 데이터 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun getModifyUserInfo(@Field("sql") sql : String) : Call<UserItem.ModifyUser>

    //회원정보수정 업데이트
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun updateModifyUserInfo(@Field("sql") sql : String) : Call<ResultModel>

    //같은 학과 차단
    @FormUrlEncoded
    @POST("/blocking/insert")
    fun insertBlocking(
        @Field("user_id") userId: String,
        @Field("dept_name") deptName: String,
        @Field("blocking_date") blockingDate: String,
        @Field("update_value") updateValue: Int
    ): Call<ResultModel>

    //같은 학과 차단 해제
    @FormUrlEncoded
    @POST("/blocking/delete")
    fun deleteBlocking(
        @Field("user_id") userId: String,
        @Field("update_value") updateValue: Int
    ) : Call<ResultModel>

    //별점 평균
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun getPointAvg(
        @Field("sql") sql : String
    ) : Call<PointModel>

    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun updateToken(@Field("sql") sql: String): Call<ResultModel>

    @FormUrlEncoded
    @POST("/common/fcm")
    fun sendFcm(
        @Field("topic") topic: String,
        @Field("title") title: String,
        @Field("content") content: String
    ): Call<ResultModel>


    //입장할 방에 자기가 있는지 체크
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun joinCheck(
        @Field("sql") sql: String
    ): Call<CountModel>

    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun addUnreadMember(
        @Field("sql") sql: String
    ): Call<ResultModel>

    //유저 정보 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun getUserInfo(@Field("sql") sql : String) : Call<UserModel.User>

    //문의사항 보내기
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun sendInquire(@Field("sql") sql : String) : Call<ResultModel>

    //문의사항 답변 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select")
    fun getInquireList(@Field("sql") sql : String) : Call<ArrayList<InquireItem.Inquire>>

    //회원탈퇴
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun signOut(@Field("sql") sql : String) : Call<ResultModel>

    //매칭조건 삽입
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun updateMatchingCondition(@Field("sql") sql : String) : Call<ResultModel>

    //방 접속 날짜 가져오기
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun getEnterDate(@Field("sql") sql: String): Call<Joined>

    //방 삭제
    @FormUrlEncoded
    @POST("/room/delete")
    fun deleteRoom(@Field("room_id") roomId: String, @Field("user_id") userId: String): Call<ResultModel>

    //방 나가기
    @FormUrlEncoded
    @POST("/room/exit")
    fun exitRoom(@Field("room_id") roomId: String, @Field("user_id") userId: String): Call<ResultModel>

    //주제구독 & 푸시알림전송
    @FormUrlEncoded
    @POST("/common/subscribe/fcm")
    fun subscribeFcm(
        @Field("room_id") roomId: String,
        @Field("user_id") userId: String
    ): Call<ResultModel>

    //알람 모두 켜기
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun allChatAlarmOn(@Field("sql") sql: String): Call<ResultModel>

    //알람 모두 끄기
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun allChatAlarmOff(@Field("sql") sql: String): Call<ResultModel>

    //알람 상태 가져오기
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun getChatAlarm(@Field("sql") sql: String): Call<CountModel>

    //알람 켜기
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun chatAlarmOn(@Field("sql") sql: String): Call<ResultModel>

    //알람 끄기
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun chatAlarmOff(@Field("sql") sql: String): Call<ResultModel>
    //프로필 부가정보(키, 취미, 성격, 소개글) 업데이트
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun updateProfileInfo(@Field("sql") sql : String) : Call<ResultModel>

    //식당목록 불러오기
    @FormUrlEncoded
    @POST("/cafeteria/get/list")
    fun getCafeteriaList(@Field("univ_name") univName : String) : Call<CafeteriaItem.CafeteriaList>

    //식당정보 불러오기
    @FormUrlEncoded
    @POST("/cafeteria/get/inform")
    fun getCafeteriaInform(@Field("cafe_no") cafeNo : Int) : Call<CafeteriaItem.Cafeteria>

    //챗히스토리 입력
    @FormUrlEncoded
    @POST("/common/sql/insert")
    fun insertChatHistory(@Field("sql") sql : String): Call<ResultModel>

    
    //버전정보 불러오기
    @FormUrlEncoded
    @POST("/common/sql/select/single")
    fun getVersionInfo(@Field("sql") sql : String) : Call<VersionModel.Version>
}