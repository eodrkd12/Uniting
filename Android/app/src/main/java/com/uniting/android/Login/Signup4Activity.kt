package com.uniting.android.Login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.uniting.android.Class.PSDialog
import com.uniting.android.Main.MainActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_signup4.*

class Signup4Activity : AppCompatActivity() {
    
    private val heightList = arrayListOf(UserItem.UserOption("150cm"), UserItem.UserOption("151cm"), UserItem.UserOption("152cm"), UserItem.UserOption("153cm"), UserItem.UserOption("154cm"), UserItem.UserOption("155cm"), UserItem.UserOption("156cm"), UserItem.UserOption("157cm"), UserItem.UserOption("158cm"), UserItem.UserOption("159cm"), UserItem.UserOption("160cm"), UserItem.UserOption("161cm"), UserItem.UserOption("162cm"), UserItem.UserOption("163cm"), UserItem.UserOption("164cm"), UserItem.UserOption("165cm"), UserItem.UserOption("166cm"), UserItem.UserOption("167cm"), UserItem.UserOption("168cm"), UserItem.UserOption("169cm"), UserItem.UserOption("170cm"), UserItem.UserOption("171cm"), UserItem.UserOption("172cm"), UserItem.UserOption("173cm"), UserItem.UserOption("174cm"), UserItem.UserOption("175cm"), UserItem.UserOption("176cm"), UserItem.UserOption("177cm"), UserItem.UserOption("178cm"), UserItem.UserOption("179cm"), UserItem.UserOption("180cm"), UserItem.UserOption("181cm"), UserItem.UserOption("182cm"), UserItem.UserOption("183cm"), UserItem.UserOption("184cm"), UserItem.UserOption("185cm"), UserItem.UserOption("186cm"), UserItem.UserOption("187cm"), UserItem.UserOption("188cm"), UserItem.UserOption("189cm"), UserItem.UserOption("190cm"))
    private val personalityList = arrayListOf(UserItem.UserOption("엉뚱"), UserItem.UserOption("활발"), UserItem.UserOption("도도"), UserItem.UserOption("엉뚱"), UserItem.UserOption("친절"), UserItem.UserOption("애교"), UserItem.UserOption("털털"), UserItem.UserOption("성실"), UserItem.UserOption("착한"), UserItem.UserOption("순수"), UserItem.UserOption("귀여운"), UserItem.UserOption("다정다감"))
    private val hobbyList = arrayListOf(UserItem.UserOption("영화보기"), UserItem.UserOption("카페가기"), UserItem.UserOption("코인노래방"), UserItem.UserOption("편맥하기"), UserItem.UserOption("수다떨기"), UserItem.UserOption("맛집찾기"), UserItem.UserOption("야구보기"), UserItem.UserOption("축구보기"), UserItem.UserOption("여행가기"), UserItem.UserOption("등산하기"), UserItem.UserOption("춤추기"), UserItem.UserOption("독서하기"))

    var heightCheck : Boolean = false
    var personalityCheck : Boolean = false
    var hobbyCheck : Boolean = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup4)

        val psDialog = PSDialog(this)

        text_user_hobby.setOnClickListener {
            psDialog.setUserOption("취미", hobbyList)
            psDialog.setSaveBtnClickListener(object : PSDialog.SaveBtnClickListener {
                override fun onClick(userOption: String) {
                    text_user_hobby.text = userOption.substring(0, userOption.length-1)
                    text_user_hobby.setTextColor(Color.parseColor("#212121"))
                    hobbyCheck = true
                }
            })
            psDialog.show()
        }
        
        text_user_personality.setOnClickListener {
            psDialog.setUserOption("성격", personalityList)
            psDialog.setSaveBtnClickListener(object : PSDialog.SaveBtnClickListener {
                override fun onClick(userOption: String) {
                    text_user_personality.text = userOption.substring(0, userOption.length-1)
                    text_user_personality.setTextColor(Color.parseColor("#212121"))
                    personalityCheck = true
                }
            })
            psDialog.show()
        }
        
        text_user_height.setOnClickListener { 
            psDialog.setUserOption("키", heightList)
            psDialog.setSaveBtnClickListener(object : PSDialog.SaveBtnClickListener {
                override fun onClick(userOption: String) {
                    text_user_height.text = userOption
                    text_user_height.setTextColor(Color.parseColor("#212121"))
                    heightCheck = true
                }
            })
            psDialog.show()
        }


        btn_signup4.setOnClickListener {
            if(!heightCheck || !hobbyCheck || !personalityCheck || edit_user_introduce.text.length < 10) {
                Toast.makeText(this, "항목을 선택해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("test", "$heightCheck , $hobbyCheck, $personalityCheck, ${edit_user_introduce.text.length}")
            } else {
                Retrofit.updateProfileInfo(text_user_height.text.toString(), text_user_hobby.text.toString(), text_user_personality.text.toString(), edit_user_introduce.text.toString()) {
                    if(it.result == "success") {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }

        btn_signup_postpone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}