package com.uniting.android.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Cafeteria.CafeteriaImageAdapter
import com.uniting.android.Cafeteria.CafeteriaItem
import com.uniting.android.Cafeteria.MenuAdapter
import com.uniting.android.Class.GMailSender
import com.uniting.android.Class.KeyboardVisibilityUtils
import com.uniting.android.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cafeteria_inform.*
import kotlinx.android.synthetic.main.activity_signup1.*
import kotlinx.android.synthetic.main.activity_signup2.*
import kotlinx.android.synthetic.main.activity_signup2.scroll_signup1
import org.jsoup.Jsoup
import kotlin.random.Random

class Signup2Activity : AppCompatActivity() {

    var code = ""
    var webMail = ""
    var reTransmission = false

    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window, onShowKeyboard = {keyboardHeight ->
            scroll_signup1.run {
                smoothScrollTo(scrollX, scrollY + keyboardHeight)
            }
        })

        val deptName = intent.getStringExtra("deptName")
        val univName = intent.getStringExtra("univName")
        val univMail = intent.getStringExtra("univMail")

        text_university_mail.text = univMail

        btn_send_certification.setOnClickListener{
            webMail = edit_university_id.text.toString() + univMail
            val random = Random
            code = random.nextInt(100000, 999999).toString()

            if(reTransmission) {
                BackgroundTask()
            }
            else {
                BackgroundTask()
                btn_send_certification.setText("재전송")
            }
        }

        edit_university_mail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(edit_university_mail.text.length == 6) {
                    btn_certify.setBackgroundResource(R.drawable.enable_rounded_button)
                    btn_certify.isEnabled = true
                }
                else {
                    btn_certify.setBackgroundResource(R.drawable.not_enable_rounded_button)
                }
            }
        })

        btn_certify.setOnClickListener {
            if(edit_university_mail.text.toString() == code) {
                btn_signup2.isEnabled = true
                btn_signup2.setBackgroundResource(R.drawable.enable_button)
            }
            else {
                Toast.makeText(this, "인증번호가 틀립니다.", Toast.LENGTH_SHORT).show()
            }
        }

        btn_signup2.setOnClickListener {
            var intent = Intent(this, Signup3Activity::class.java)
            intent.putExtra("mail", webMail)
            intent.putExtra("univName", univName)
            intent.putExtra("deptName", deptName)

            finish()
            startActivity(intent)
        }




    }


    var backgroundtask: Disposable? = null

    fun BackgroundTask() {
        backgroundtask =
            Observable.fromCallable<Any> {
                try {
                    var mailSender: GMailSender = GMailSender("ljs950113@gmail.com", "limjs165879!", code)
                    mailSender.sendMail(
                        "Uniting 이메일 인증"
                        , "안녕하세요.\n" +
                                "아래 인증 코드를 애플리케이션에서 입력하여 회원가입을 진행해주십시오.\n" +
                                "인증코드 : [${code}]\n" +
                                "감사합니다."
                        , webMail
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                false
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result: Any? ->
                    Toast.makeText(this,"인증번호를 전송하였습니다.", Toast.LENGTH_SHORT).show()
                    reTransmission = true
                    backgroundtask?.dispose()
                }
    }
}