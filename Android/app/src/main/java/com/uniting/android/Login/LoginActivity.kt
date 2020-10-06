package com.uniting.android.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.uniting.android.Class.UserInfo
import com.uniting.android.Main.MainActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_signup.setOnClickListener {
            var intent = Intent(this, Signup1Activity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            Retrofit.login(edit_login_id.text.toString(), edit_login_pw.text.toString()) {
                when(it) {
                    0 -> {
                    }
                    1 -> {
                        var intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("id", edit_login_id.text.toString())
                        val pref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("ID", edit_login_id.text.toString())
                        editor.putString("PW", edit_login_pw.text.toString())
                            .apply()
                        UserInfo.ID = edit_login_id.text.toString()
                        UserInfo.PW = edit_login_pw.text.toString()
                        startActivity(intent)

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
                    2 -> {
                        Toast.makeText(this, "비밀번호가 틀림", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}