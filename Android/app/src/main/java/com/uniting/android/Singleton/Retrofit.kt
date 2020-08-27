package com.uniting.android.Singleton

import android.util.Log
import com.uniting.android.Cafeteria.CafeteriaItem
import com.uniting.android.Chat.ChatItem
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DataModel.ResultModel
import com.uniting.android.Interface.RetrofitService
import com.uniting.android.Item.Test
import kotlinx.android.synthetic.main.activity_write_review.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.text.SimpleDateFormat

object Retrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://52.78.27.41:1901")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(RetrofitService::class.java)

    fun curDate() : String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())
        return curDate
    }

    fun insertChat(roomId: String, userId: String, userNickname: String, content: String, current: String, unreadCount: Int, systemChat: Int, callback: (ResultModel) -> Unit) {

        val sql = "INSERT INTO chat(room_id,user_id,user_nickname,chat_content,chat_time,unread_count,system_chat) " +
                "VALUES('${roomId}','${userId}','${userNickname}','${content}','${current}',${unreadCount},${systemChat})"

        service.insert(sql).enqueue(object: Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                Log.d("test",t.message.toString())
            }
        })
    }

    fun getMyRoom(userId: String, callback: (ArrayList<Room>) -> Unit) {

        val sql = "SELECT room.room_id AS room_id, room_title, category, room_date , room_introduce, univ_name, maker" +
                "FROM room, joined" +
                "WHERE 'room.room_id' = 'joined.room_id' AND user_id = '${userId}'"

        service.getMyRoom(sql).enqueue(object: Callback<ArrayList<Room>>{
            override fun onResponse(
                call: Call<ArrayList<Room>>,
                response: Response<ArrayList<Room>>
            ) {
                callback(response.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<Room>>, t: Throwable) {
                Log.d("test",t.message.toString())
            }
        })
    }

    fun getReview(cafeteriaName: String, callback: (ArrayList<CafeteriaItem.Review>) -> Unit) {

        val sql = "select * from review where cafe_name='${cafeteriaName}'"

        service.getReview(sql).enqueue(object: Callback<ArrayList<CafeteriaItem.Review>>{
            override fun onResponse(
                call: Call<ArrayList<CafeteriaItem.Review>>,
                response: Response<ArrayList<CafeteriaItem.Review>>
            ) {
                callback(response.body()!!)
            }
            override fun onFailure(call: Call<ArrayList<CafeteriaItem.Review>>, t: Throwable) {
                Log.d("test",t.message.toString())
            }
        })
    }

    fun insertReview(cafeteriaName: String, reviewContent: String, reviewPoint:Int, reviewType: String, imagePath: String, callback : (ResultModel) -> Unit) {
        if(reviewType == "noimage") {
            service.insertNoImageReview(UserInfo.ID, UserInfo.NICKNAME, cafeteriaName, reviewContent, curDate(), reviewPoint).enqueue(object: Callback<ResultModel> {
                override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                    callback(response.body()!!)
                }

                override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                    Log.d("test", t.toString())
                }
            })
        }
        else {
            val requestFile = RequestBody.create(MediaType.parse("image/*"), File(imagePath))
            val uploadFile = MultipartBody.Part.createFormData("img", imagePath, requestFile)
            service.insertReview(UserInfo.ID, UserInfo.NICKNAME, cafeteriaName, reviewContent, curDate(), reviewPoint, uploadFile).enqueue(object: Callback<ResultModel> {
                override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                    callback(response.body()!!)
                }

                override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                    Log.d("test", t.toString())
                }
            })
        }
    }

}