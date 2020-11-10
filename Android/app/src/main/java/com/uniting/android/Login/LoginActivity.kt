package com.uniting.android.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.uniting.android.Class.MatchingCondition
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.UserInfo
import com.uniting.android.Main.MainActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_signup.setOnClickListener {
            var intent = Intent(this, Signup1Activity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            Retrofit.login(edit_login_id.text.toString(), edit_login_pw.text.toString()) {
                when(it.result) {
                    "success" -> {
                        UserInfo.ID = it.userId
                        UserInfo.PW = it.userPw
                        UserInfo.GENDER = it.userGender
                        UserInfo.NICKNAME = it.userNickname
                        UserInfo.BLOCKINGDEPT = it.blockingDept
                        UserInfo.UNIV = it.univName
                        UserInfo.DEPT = it.deptName

                        MatchingCondition.AGE = it.matchingAge
                        MatchingCondition.DEPT = it.matchingDept
                        MatchingCondition.HEIGHT = it.matchingHeight
                        MatchingCondition.PERSONALITY = it.matchingPersonality
                        MatchingCondition.HOBBY = it.matchingHobby

                        val pref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("ID", UserInfo.ID)
                        editor.putString("PW", UserInfo.PW)
                            .apply()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        FirebaseInstanceId.getInstance().instanceId
                            .addOnCompleteListener(OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w("test", "getInstanceId failed", task.exception)
                                    return@OnCompleteListener
                                }
                                else {
                                    // Get new Instance ID token
                                    val token = task.result?.token

                                    Retrofit.updateToken(UserInfo.ID, token!!)
                                }
                            })
                    }
                    "fail" -> {
                        Toast.makeText(this, "아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                    "blacklist" -> {
                        Toast.makeText(this, "블랙리스트", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}