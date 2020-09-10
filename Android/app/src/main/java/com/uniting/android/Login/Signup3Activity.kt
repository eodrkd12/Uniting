package com.uniting.android.Login

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uniting.android.Class.KeyboardVisibilityUtils
import com.uniting.android.Class.PSDialog
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_signup1.scroll_signup1
import kotlinx.android.synthetic.main.activity_signup3.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class Signup3Activity : AppCompatActivity() {

    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils
    
    private val cityList = arrayListOf(UserItem.UserOption("서울"), UserItem.UserOption("경기"), UserItem.UserOption("인천"), UserItem.UserOption("대전"), UserItem.UserOption("대구"), UserItem.UserOption("부산"), UserItem.UserOption("울산"), UserItem.UserOption("광주"), UserItem.UserOption("강원"), UserItem.UserOption("세종"), UserItem.UserOption("충북"),
        UserItem.UserOption("충남"), UserItem.UserOption("경북"), UserItem.UserOption("전남"), UserItem.UserOption("제주"), UserItem.UserOption("해외"))

    private var idCheck = 0
    private var pw1Check = 0
    private var pw2Check = 0

    var id = ""
    var pw = ""
    var nickname = ""
    var gender = ""
    var birthday = ""
    var city = ""
    var webMail = ""
    var univName = ""
    var deptName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup3)

        webMail = intent.getStringExtra("mail")!!
        univName = intent.getStringExtra("univName")!!
        deptName = intent.getStringExtra("deptName")!!

        val psDialog = PSDialog(this)

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window, onShowKeyboard = {keyboardHeight ->
            scroll_signup1.run {
                smoothScrollTo(scrollX, scrollY + keyboardHeight)
            }
        })

        edit_user_id.filters = arrayOf<InputFilter>(IdFilter())

        btn_check_id.setOnClickListener {
            if(edit_user_id.text.toString().replace(" ", "").length < 5) {
                text_check_id.text = "아이디는 5자리 이상이어야 합니다."
                text_check_id.setTextColor(Color.parseColor("#FF0000"))
            }
            else {
                if(id == "") {
                    idCheck()
                }
                else {
                    if(id != edit_user_id.text.toString()) {
                        Retrofit.idDelete(id) {
                            if(it.result == "success") {
                                idCheck()
                            }
                        }
                    }
                    else if(id == edit_user_id.text.toString())
                    {
                        text_check_id.text = "사용가능한 아이디 입니다."
                        text_check_id.setTextColor(Color.parseColor("#008000"))
                        idCheck = 1
                    }
                }
            }
        }

        edit_user_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                idCheck = 0
                text_check_id.text = ""
            }
        })

        edit_user_password.setOnFocusChangeListener { view, b ->
            if(!b)
            {
                pwLengthCheck()
            }
        }

        edit_user_check_password.setOnFocusChangeListener { view, b ->
            if(!b)
            {
                pwEqualCheck()
            }
        }

        radiogroup_gender.clipToOutline = true
        radiogroup_gender.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.radio_male -> {
                    radio_female.setBackgroundColor(Color.TRANSPARENT)
                    radio_female.setTextColor(Color.parseColor("#616161"))
                    radio_male.setBackgroundColor(Color.parseColor("#00BFFF"))
                    radio_male.setTextColor(Color.WHITE)
                    gender = "M"
                }
                R.id.radio_female -> {
                    radio_male.setBackgroundColor(Color.TRANSPARENT)
                    radio_male.setTextColor(Color.parseColor("#616161"))
                    radio_female.setBackgroundColor(Color.parseColor("#00BFFF"))
                    radio_female.setTextColor(Color.WHITE)
                    gender = "F"
                }
            }
        }

        text_birthday.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@Signup3Activity, R.style.MySpinnerDatePickerStyle,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    try {
                        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val d: Date = simpleDateFormat.parse(year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString())
                        birthday = simpleDateFormat.format(d)
                        text_birthday.text = simpleDateFormat.format(d)
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

        text_user_city.setOnClickListener {
            psDialog.setUserOption("거주지", cityList)
            psDialog.setSaveBtnClickListener(object: PSDialog.SaveBtnClickListener {
                override fun onClick(userOption: String) {
                    city = userOption
                    text_user_city.text = userOption
                }
            })

            psDialog.show()
        }

        btn_signup3.setOnClickListener {
            pwLengthCheck()
            pwEqualCheck()
            nickCheck()

            if(idCheck == 0)
            {
                Toast.makeText(this@Signup3Activity, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(pw2Check == 0 || pw1Check == 0)
            {
                Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(nickname == "")
            {
                Toast.makeText(this, "닉네임을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(gender == "")
            {
                Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(birthday == "")
            {
                Toast.makeText(this, "입학년도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(city == "")
            {
                Toast.makeText(this, "거주지를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                Retrofit.signUp(id, pw, nickname, birthday, city, gender, univName, deptName, webMail, "") {

                }
            }

        }
    }


    //아이디 특수문자 입력방지 필터
    class IdFilter : InputFilter {
        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int) : CharSequence?{
            var ps : Pattern = Pattern.compile("^[a-zA-Z0-9]+$")
            if(!ps.matcher(source).matches()) {
                return ""
            }
            return null
        }
    }

    private fun pwLengthCheck() {
        if(edit_user_password.text.length < 8)
        {
            pw1Check = 0
            text_check_password.text = "비밀번호는 8자리 이상이어야 합니다."
            text_check_password.setTextColor(Color.parseColor("#FF0000"))
        }
        else
        {
            pw1Check = 1
            text_check_password.text = "사용가능한 비밀번호 입니다."
            text_check_password.setTextColor(Color.parseColor("#008000"))
        }
    }

    private fun pwEqualCheck() {
        if(edit_user_password.text.toString().equals(edit_user_check_password.text.toString()) && (pw1Check == 1))
        {
            text_check_equal_password.text = "비밀번호가 일치합니다."
            text_check_equal_password.setTextColor(Color.parseColor("#008000"))
            pw = edit_user_password.text.toString()
            pw2Check = 1
        }
        else{
            text_check_equal_password.text = "비밀번호가 일치하지 않습니다."
            text_check_equal_password.setTextColor(Color.parseColor("#FF0000"))
            pw = ""
            pw2Check = 0
        }
    }

    private fun idCheck() {
        Retrofit.idCheck(edit_user_id.text.toString()) {
            when(it.count) {
                0 -> {
                    text_check_id.text = "중복된 아이디 입니다."
                    text_check_id.setTextColor(Color.parseColor("#FF0000"))
                    id = ""
                    idCheck = 0
                }
                1 -> {
                    Retrofit.idInsert(edit_user_id.text.toString()) {
                        when(it.result) {
                            "success" -> {
                                Log.d("test", "임시 아이디 삽입완료")
                                text_check_id.text = "사용가능한 아이디입니다."
                                text_check_id.setTextColor(Color.parseColor("#008000"))
                                idCheck = 1
                                id = edit_user_id.text.toString()
                            }
                            else -> {
                                Log.d("test", "임시 아이디 삽입오류")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun nickCheck() {
        if(edit_user_nickname.text.toString().replace(" ", "").length > 3 && edit_user_nickname.text.toString().replace(" ", "").length < 11) {
            nickname = edit_user_nickname.text.toString().replace(" ", "")
        }
    }
}