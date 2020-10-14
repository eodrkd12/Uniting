package com.uniting.android.Option

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uniting.android.Class.PSDialog
import com.uniting.android.Class.UserInfo
import com.uniting.android.Login.UserItem
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_signup3.*
import java.text.SimpleDateFormat
import java.util.*

class ModifyActivity : AppCompatActivity() {

    private val cityList = arrayListOf(
        UserItem.UserOption("서울"), UserItem.UserOption("경기"), UserItem.UserOption("인천"), UserItem.UserOption("대전"), UserItem.UserOption("대구"), UserItem.UserOption("부산"), UserItem.UserOption("울산"), UserItem.UserOption("광주"), UserItem.UserOption("강원"), UserItem.UserOption("세종"), UserItem.UserOption("충북"),
        UserItem.UserOption("충남"), UserItem.UserOption("경북"), UserItem.UserOption("전남"), UserItem.UserOption("제주"), UserItem.UserOption("해외"))

    private val personalityList = arrayListOf(UserItem.UserOption("엉뚱"), UserItem.UserOption("활발"), UserItem.UserOption("도도"), UserItem.UserOption("엉뚱"), UserItem.UserOption("친절"), UserItem.UserOption("애교"), UserItem.UserOption("털털"), UserItem.UserOption("성실"), UserItem.UserOption("착한"), UserItem.UserOption("순수"), UserItem.UserOption("귀여운"), UserItem.UserOption("다정다감"))
    private val hobbyList = arrayListOf(UserItem.UserOption("영화보기"), UserItem.UserOption("카페가기"), UserItem.UserOption("코인노래방"), UserItem.UserOption("편맥하기"), UserItem.UserOption("수다떨기"), UserItem.UserOption("맛집찾기"), UserItem.UserOption("야구보기"), UserItem.UserOption("축구보기"), UserItem.UserOption("여행가기"), UserItem.UserOption("등산하기"), UserItem.UserOption("춤추기"), UserItem.UserOption("독서하기"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        val psDialog = PSDialog(this)

        Retrofit.getModifyUserInfo(UserInfo.ID) {
            text_modify_birthday.text = it.userBirthday
            text_modify_city.text = it.userCity
            text_modify_department_name.text = it.deptName
            text_modify_sign_date.text = it.userSignDate.substring(0, 10)
            text_modify_university_name.text = it.univName
            text_modify_web_mail.text = it.userEmail
            edit_modify_nickname.setText(it.userNickname)
            text_modify_height.text = it.userHeight

            if(it.userIntroduce == null) {
                text_modify_introduce.setTextColor(Color.parseColor("#00BFFF"))
            } else {
                text_modify_introduce.text = it.userIntroduce
            }

            if(it.userHobby == null) {
                text_modify_hobby.setTextColor(Color.parseColor("#00BFFF"))
            } else {
                text_modify_hobby.text = it.userHobby
            }

            if(it.userPersonality == null) {
                text_modify_personality.setTextColor(Color.parseColor("#00BFFF"))
            } else {
                text_modify_personality.text = it.userPersonality
            }

            if(it.userHeight == null) {
                text_modify_height.setTextColor(Color.parseColor("#00BFFF"))
            } else {
                text_modify_height.text = it.userHeight
            }


            text_modify_update.setOnClickListener {
                Retrofit.updateModifyUserInfo(
                    UserInfo.ID,
                    edit_modify_nickname.text.toString(),
                    text_modify_birthday.text.toString(),
                    text_modify_city.text.toString(),
                    text_modify_introduce.text.toString(),
                    text_modify_hobby.text.toString(),
                    text_modify_personality.text.toString()
                ) {
                    finish()
                }
            }

            text_modify_personality.setOnClickListener {
                psDialog.setUserOption("성격", personalityList)
                psDialog.setSaveBtnClickListener(object : PSDialog.SaveBtnClickListener {
                    override fun onClick(userOption: String) {
                        text_modify_personality.text = userOption.substring(0, userOption.length-1)
                        text_modify_personality.setTextColor(Color.parseColor("#212121"))
                    }
                })
                psDialog.show()
            }

            text_modify_hobby.setOnClickListener {
                psDialog.setUserOption("취미", hobbyList)
                psDialog.setSaveBtnClickListener(object : PSDialog.SaveBtnClickListener {
                    override fun onClick(userOption: String) {
                        text_modify_hobby.text = userOption.substring(0, userOption.length-1)
                        text_modify_hobby.setTextColor(Color.parseColor("#212121"))
                    }
                })
                psDialog.show()
            }

            text_modify_city.setOnClickListener {
                psDialog.setUserOption("거주지", cityList)
                psDialog.setSaveBtnClickListener(object: PSDialog.SaveBtnClickListener {
                    override fun onClick(userOption: String) {
                        text_modify_city.text = userOption
                    }
                })
                psDialog.show()
            }

            text_modify_introduce.setOnClickListener {
                psDialog.setIntroduce()
                psDialog.setSaveBtnClickListener(object : PSDialog.SaveBtnClickListener {
                    override fun onClick(userOption: String) {
                        text_modify_introduce.text = userOption
                        text_modify_introduce.setTextColor(Color.parseColor("#212121"))
                    }
                })
                psDialog.show()
            }

            text_modify_birthday.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    this, R.style.MySpinnerDatePickerStyle,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        try {
                            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                            val d: Date =
                                simpleDateFormat.parse(year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString())
                            text_modify_birthday.text = simpleDateFormat.format(d)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    2001,
                    1,
                    1
                )
                datePickerDialog.show()
            }

            btn_modify_back.setOnClickListener {
                val saveDialog = PSDialog(this)
                saveDialog.setCheckSave()
                saveDialog.show()
            }

        }
    }

    override fun onBackPressed() {
        val psDialog = PSDialog(this)
        psDialog.setCheckSave()
        psDialog.show()
    }
}