package com.uniting.android.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            Retrofit.login(edit_login_id.text.toString(), edit_login_pw.text.toString()) {
                when(it) {
                    0 -> {

                    }
                    1 -> {
                        Toast.makeText(this, "로그인에 성공", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        Toast.makeText(this, "비밀번호가 틀림", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}