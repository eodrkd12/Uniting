package com.uniting.android.Singleton

import android.util.Log
import com.uniting.android.Cafeteria.CafeteriaItem
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DataModel.*
import com.uniting.android.Interface.RetrofitService
import com.uniting.android.Login.UniversityItem
import com.uniting.android.Login.UserItem
import com.uniting.android.Option.InquireItem
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

    fun insertChat(chat: Chat, callback: (ResultModel) -> Unit) {

        val sql = "INSERT INTO chat " +
                "VALUES('${chat.chat_id}','${chat.room_id}','${chat.user_id}','${chat.user_nickname}','${chat.chat_content}','${chat.chat_time}','${chat.unread_member}',${chat.system_chat})"

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

        val sql = "SELECT room.room_id AS room_id, room_title, category, room_date , room_introduce, univ_name, maker " +
                "FROM room, joined " +
                "WHERE room.room_id = joined.room_id AND user_id = '${userId}'"

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

        val sql = "select * from review where cafe_name='${cafeteriaName}' order by review_date desc"

        service.getReview(sql).enqueue(object: Callback<ArrayList<CafeteriaItem.Review>>{
            override fun onResponse(
                call: Call<ArrayList<CafeteriaItem.Review>>,
                response: Response<ArrayList<CafeteriaItem.Review>>
            ) {
                callback(response.body()!!)

                for(i in response.body()!!)
                    Log.d("test", i.toString())
            }
            override fun onFailure(call: Call<ArrayList<CafeteriaItem.Review>>, t: Throwable) {
                Log.d("test",t.message.toString())
            }
        })
    }

    fun insertReview(cafeteriaName: String, reviewContent: String, reviewPoint:Int, reviewType: String, imagePath: String, callback : (ResultModel) -> Unit) {
        if(reviewType == "noimage") {
            service.insertNoImageReview(UserInfo.ID, UserInfo.NICKNAME, cafeteriaName, reviewContent, curDate(), reviewPoint, "noimage").enqueue(object: Callback<ResultModel> {
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

    fun randomMatching(callback : (ArrayList<ProfileModel.Profile>) -> Unit) {
        val sql = "select * from user where (user_id not in (select user_id from chathistory where partner_id='${UserInfo.ID}')) and (user_id not in (select partner_id from chathistory where user_id='${UserInfo.ID}')) and (user_id not in (select user_id from blocking where blocking = '${UserInfo.DEPT}')) and user_id <> '${UserInfo.ID}' and user_gender <> '${UserInfo.GENDER}' and univ_name = '${UserInfo.UNIV}' order by rand() limit 1;"
        val blockingSql = "select * from user where (user_id not in (select user_id from chathistory where partner_id='${UserInfo.ID}')) and (user_id not in (select partner_id from chathistory where user_id='${UserInfo.ID}')) and user_id <> '${UserInfo.ID}' and user_gender <> '${UserInfo.GENDER}' and dept_name <> '${UserInfo.DEPT}' and univ_name = '${UserInfo.UNIV}' order by rand() limit 1;"


        if(UserInfo.BLOCKINGDEPT == 0) {
            service.randomMatching(sql).enqueue(object: Callback<ArrayList<ProfileModel.Profile>> {
                override fun onResponse(call: Call<ArrayList<ProfileModel.Profile>>, response: Response<ArrayList<ProfileModel.Profile>>) {
                    callback(response.body()!!)
                }
                override fun onFailure(call: Call<ArrayList<ProfileModel.Profile>>, t: Throwable) {
                    Log.d("test", t.toString())
                }
            })
        } else {
            service.randomMatching(blockingSql).enqueue(object: Callback<ArrayList<ProfileModel.Profile>> {
                override fun onResponse(call: Call<ArrayList<ProfileModel.Profile>>, response: Response<ArrayList<ProfileModel.Profile>>) {
                    callback(response.body()!!)
                }
                override fun onFailure(call: Call<ArrayList<ProfileModel.Profile>>, t: Throwable) {
                    Log.d("test", t.toString())
                }
            })
        }
    }

    fun smartMatching(
        height: String,
        age: String,
        department: String,
        hobby: String,
        personality: String,
        callback: (ArrayList<ProfileModel.Profile>) -> Unit
    ) {
        var minHeight = height.split(" ~ ")[0]
        var maxHeight = height.split(" ~ ")[1]

        var minAge = age.split(" ~ ")[0]
        var maxAge = age.split(" ~ ")[1]

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())
        var year = curDate.split("-")[0]

        var minYear = year.toInt() - minAge.toInt() + 1
        var maxYear = year.toInt() - maxAge.toInt() + 1

        var sql = "SELECT * FROM user " +
                "WHERE (user_id NOT IN (SELECT user_id FROM chathistory WHERE partner_id='${UserInfo.ID}')) " +
                "AND (user_id NOT IN (SELECT partner_id FROM chathistory WHERE user_id='${UserInfo.ID}')) " +
                "AND (user_gender <> '${UserInfo.GENDER}') " +
                "AND (user_id <> '${UserInfo.ID}') " +
                "AND (user_height >= '${minHeight}') " +
                "AND (user_height <= '${maxHeight}') " +
                "AND (user_birthday <= '${minYear}-12-31') " +
                "AND (user_birthday >= '${maxYear}-01-01') "

        if(department != "") {
            sql += "AND ("
            var departmentList = department.split(", ")
            departmentList.forEach {
                if (it == departmentList[0])
                    sql += "dept_name LIKE '%${it}%' "
                else
                    sql += "OR dept_name LIKE '%${it}%' "
            }
            sql += ")"
        }

        if(hobby != "") {
            sql += "AND ("
            var hobbyList = hobby.split(", ")
            hobbyList.forEach {
                if (it == hobbyList[0])
                    sql += "user_hobby LIKE '%${it}%' "
                else
                    sql += "OR (user_hobby LIKE '%${it}%' "
            }
            sql += ")"
        }

        if(personality != "") {
            sql += "AND ("
            var personalityList = personality.split(", ")
            personalityList.forEach {
                if (it == personalityList[0])
                    sql += "(user_personality LIKE '%${it}%' "
                else
                    sql += "OR (user_personality LIKE '%${it}%' "
            }
            sql += ")"
        }

        sql += "ORDER BY RAND() LIMIT 1"

        service.smartMatching(sql).enqueue(object: Callback<ArrayList<ProfileModel.Profile>> {
            override fun onFailure(call: Call<ArrayList<ProfileModel.Profile>>, t: Throwable) {
                Log.d("test",t.message!!)
            }

            override fun onResponse(
                call: Call<ArrayList<ProfileModel.Profile>>,
                response: Response<ArrayList<ProfileModel.Profile>>
            ) {
                callback(response.body()!!)
            }

        })
    }

    fun getOpenChatList(
        univName: String,
        category: String?,
        callback: (ArrayList<Room>) -> Unit
    ) {
        val sql = "SELECT * FROM room WHERE univ_name = '${univName}' AND category = '${category}'"

        service.getOpenChatList(sql).enqueue(object: Callback<ArrayList<Room>>{
            override fun onFailure(call: Call<ArrayList<Room>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ArrayList<Room>>,
                response: Response<ArrayList<Room>>
            ) {
                callback(response.body()!!)
            }

        })


    }

    fun deleteReview(reviewId: Int, imagePath: String, callback : (ResultModel) -> Unit) {
        service.deleteReview(reviewId, imagePath).enqueue(object:Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                Log.d("test", t.toString())
            }

        })
    }

    fun getUniversity(callback : (ArrayList<UniversityItem.University>) -> Unit) {
        service.getUniversity().enqueue(object : Callback<ArrayList<UniversityItem.University>> {
            override fun onFailure(call: Call<ArrayList<UniversityItem.University>>, t: Throwable) {
                Log.d("test", t.toString())
            }

            override fun onResponse(call: Call<ArrayList<UniversityItem.University>>, response: Response<ArrayList<UniversityItem.University>>) {
                callback(response.body()!!)
                for(i in response.body()!!) {
                    Log.d("test", i.toString())
                }
            }
        })
    }

    fun getDepartment(univName: String, callback: (ArrayList<UniversityItem.Department>) -> Unit) {
        val sql = "SELECT dept_name FROM department WHERE univ_name='${univName}'"

        service.getDepartment(sql).enqueue(object: Callback<ArrayList<UniversityItem.Department>> {
            override fun onFailure(call: Call<ArrayList<UniversityItem.Department>>, t: Throwable) {
                Log.d("test", t.toString())
            }

            override fun onResponse(call: Call<ArrayList<UniversityItem.Department>>, response: Response<ArrayList<UniversityItem.Department>>) {
                callback(response.body()!!)
            }
        })
    }

    fun createRoom(
        roomId: String,
        title: String,
        category: String,
        curDate: String,
        introduce: String,
        univName: String,
        userId: String,
        callback: (ResultModel) -> Unit
    ) {

        var sql = "INSERT INTO room " +
                "VALUES('${roomId}','${title}','${category}','${curDate}','${introduce}','${univName}','${userId}')"

        service.createRoom(sql).enqueue(object: Callback<ResultModel>{
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }
        })
    }


    fun joinRoom(roomId: String, userId: String,date: String, callback: (ResultModel) -> Unit) {
        var sql = "INSERT INTO joined " +
                "VALUES('${roomId}','${userId}','${date}',1)"

        service.joinRoom(sql).enqueue(object: Callback<ResultModel>{
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }

        })
    }

    fun idCheck(userId: String, callback: (CountModel) -> Unit) {
        var sql = "SELECT count(user_id) as count FROM user WHERE user_id='${userId}'"

        service.idCheck(sql).enqueue(object:Callback<CountModel> {
            override fun onFailure(call: Call<CountModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<CountModel>, response: Response<CountModel>) {
                callback(response.body()!!)
            }
        })
    }

    fun idInsert(userId: String, callback: (ResultModel) -> Unit) {
        val sql = "INSERT INTO user(user_id, blocking_dept, alarm_visit, alarm_openprofile) values('${userId}', 0, 0, 0)"

        service.idInsert(sql).enqueue(object: Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }
        })
    }

    fun idDelete(userId: String, callback: (ResultModel) -> Unit) {
        val sql = "DELETE FROM user WHERE user_id='${userId}'"

        service.idDelete(sql).enqueue(object: Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }
        })
    }

    fun signUp(userId: String, userPw: String, userNickname: String, userBirthday: String, userCity: String, userGender: String, univName: String, deptName: String, webMail: String, enterYear: String, callback: (ResultModel) -> Unit) {
        val sql = "UPDATE user SET user_pw = '${userPw}', user_nickname = '${userNickname}', user_birthday = '${userBirthday}'," +
                "user_gender = '${userGender}', user_email = '${webMail}', univ_name = '${univName}', dept_name = '${deptName}'," +
                "enter_year = '${enterYear}', user_city = '${userCity}', user_signdate = '${curDate()}' WHERE user_id = '${userId}'"
        service.signUp(sql).enqueue(object: Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }
        })
    }

    fun accountCheck(webMail: String, callback: (CountModel) -> Unit) {
        var sql = "SELECT count(user_email) as count FROM user WHERE user_email='${webMail}'"

        service.accountCheck(sql).enqueue(object:Callback<CountModel> {
            override fun onFailure(call: Call<CountModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<CountModel>, response: Response<CountModel>) {
                callback(response.body()!!)
            }
        })
    }
    
    fun getMembers(roomId: String, callback: (ArrayList<MemberModel>) -> Unit) {

        val sql = "SELECT joined.user_id AS user_id, user_nickname FROM joined, user WHERE room_id = '${roomId}' AND joined.user_id = user.user_id"


        service.getMembers(sql).enqueue(object: Callback<ArrayList<MemberModel>> {
            override fun onFailure(call: Call<ArrayList<MemberModel>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ArrayList<MemberModel>>,
                response: Response<ArrayList<MemberModel>>
            ) {
                callback(response.body()!!)
            }

        })
    }
    
    fun login(userId: String, userPw: String, callback: (Int) -> Unit) {
        val sql = "SELECT user_pw as result FROM user WHERE user_id='${userId}'"

        service.login(sql).enqueue(object: Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                if(t is java.io.EOFException){
                }

                callback(0)
            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if(response.body()!!.result == userPw) {
                    callback(1)
                } else {
                    callback(2)
                }
            }
        })
    }

    fun getModifyUserInfo(userId: String, callback: (UserItem.ModifyUser) -> Unit) {
        val sql = "SELECT user_nickname, user_birthday, user_gender, user_email, univ_name, dept_name, enter_year, user_city, user_signdate FROM user WHERE user_id='${userId}'"

        service.getModifyUserInfo(sql).enqueue(object: Callback<UserItem.ModifyUser> {
            override fun onFailure(call: Call<UserItem.ModifyUser>, t: Throwable) {

            }

            override fun onResponse(call: Call<UserItem.ModifyUser>, response: Response<UserItem.ModifyUser>) {
                callback(response.body()!!)
            }
        })
    }

    fun addUnreadMember(roomId: String, userId: String, callback: (ResultModel) -> Unit) {
        val sql = "UPDATE chat SET unread_member=CONCAT(unread_member,'${userId}|') WHERE room_id = '${roomId}'"

        service.addUnreadMember(sql).enqueue(object : Callback<ResultModel>{
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }

        })
    }

    fun updateModifyUserInfo(userId: String, userNickname: String, userBirthday: String, userCity: String, callback : (ResultModel) -> Unit) {
        val sql = "UPDATE user SET user_nickname='${userNickname}', user_birthday = '${userBirthday}', user_city = '${userCity}' WHERE user_id='${userId}'"

        service.updateModifyUserInfo(sql).enqueue(object: Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }
        })
    }

    fun updateBlocking(userId: String, deptName: String?, blockingDate : String?,updateType: String, callback: (ResultModel) -> Unit) {
        when(updateType) {
            "insert" -> {
                service.insertBlocking(userId, deptName!!, blockingDate!!, 1).enqueue(object: Callback<ResultModel> {
                    override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<ResultModel>,
                        response: Response<ResultModel>
                    ) {
                        callback(response.body()!!)
                    }
                })
            }
            "delete" -> {
                service.deleteBlocking(userId, 0).enqueue(object: Callback<ResultModel> {
                    override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<ResultModel>,
                        response: Response<ResultModel>
                    ) {
                        callback(response.body()!!)
                    }
                })
            }
        }
    }

    fun getPointAvg(cafeName: String, callback: (PointModel) -> Unit) {
        val sql = "SELECT AVG(review_point) as avg FROM review WHERE cafe_name='${cafeName}'"

        service.getPointAvg(sql).enqueue(object: Callback<PointModel> {
            override fun onFailure(call: Call<PointModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<PointModel>, response: Response<PointModel>) {
                callback(response.body()!!)
            }
        })
    }

    fun updateToken(userId: String, token: String?) {
        val sql = "UPDATE user SET token='${token!!}' WHERE user_id = '${userId}'"

        service.updateToken(sql).enqueue(object: Callback<ResultModel>{
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {

            }
        })
    }

    fun joinCheck(roomId: String, userId: String, callback: (CountModel) -> Unit) {

        val sql = "SELECT COUNT(*) AS count FROM joined WHERE room_id = '${roomId}' AND user_id = '${userId}'"

        service.joinCheck(sql).enqueue(object: Callback<CountModel> {
            override fun onFailure(call: Call<CountModel>, t: Throwable) {

            }
            override fun onResponse(call: Call<CountModel>, response: Response<CountModel>) {
                callback(response.body()!!)
            }

        })
    }

    fun sendFcm(topic: String, title: String, content: String) {
        service.sendFcm(topic,title,content).enqueue(object: Callback<ResultModel>{
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
            }

        })
    }

    fun getUserInfo(userId : String, callback: (UserModel.User) -> Unit) {
        val sql = "SELECT * FROM user WHERE user_id='${userId}'"

        service.getUserInfo(sql).enqueue(object : Callback<UserModel.User> {
            override fun onFailure(call: Call<UserModel.User>, t: Throwable) {

            }

            override fun onResponse(call: Call<UserModel.User>, response: Response<UserModel.User>) {
                callback(response.body()!!)
            }
        })
    }

    fun sendInquire(userId : String, inquireDate: String, inquireTitle: String, inquireContent: String, inquireType: String, callback : (ResultModel) -> Unit) {
        val sql = "INSERT INTO inquire(user_id, inquire_date, inquire_title, inquire_content, inquire_type) values('${userId}', '${inquireDate}', '${inquireTitle}', '${inquireContent}', '${inquireType}')"

        service.sendInquire(sql).enqueue(object : Callback<ResultModel> {
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                callback(response.body()!!)
            }
        })
    }

    fun getInquireList(userId: String, callback : (ArrayList<InquireItem.Inquire>) -> Unit) {
        val sql = "SELECT * FROM inquire WHERE user_id='${userId}' ORDER BY inquire_date DESC"

        service.getInquireList(sql).enqueue(object : Callback<ArrayList<InquireItem.Inquire>> {
            override fun onFailure(call: Call<ArrayList<InquireItem.Inquire>>, t: Throwable) {

            }

            override fun onResponse(call: Call<ArrayList<InquireItem.Inquire>>, response: Response<ArrayList<InquireItem.Inquire>>) {
                callback(response.body()!!)
            }
        })
    }


}