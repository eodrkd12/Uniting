package com.uniting.android.Login

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uniting.android.Class.KeyboardVisibilityUtils
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_signup1.scroll_signup1
import kotlinx.android.synthetic.main.activity_signup3.*
import java.text.SimpleDateFormat
import java.util.*


class Signup3Activity : AppCompatActivity() {

    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup3)

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window, onShowKeyboard = {keyboardHeight ->
            scroll_signup1.run {
                smoothScrollTo(scrollX, scrollY + keyboardHeight)
            }
        })

        radiogroup_gender.clipToOutline = true

        radiogroup_gender.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.radio_male -> {
                    radio_female.setBackgroundColor(Color.TRANSPARENT)
                    radio_female.setTextColor(Color.BLACK)
                    radio_male.setBackgroundColor(Color.parseColor("#00BFFF"))
                    radio_male.setTextColor(Color.WHITE)
                }
                R.id.radio_female -> {
                    radio_male.setBackgroundColor(Color.TRANSPARENT)
                    radio_male.setTextColor(Color.BLACK)
                    radio_female.setBackgroundColor(Color.parseColor("#00BFFF"))
                    radio_female.setTextColor(Color.WHITE)
                }
            }
        }

        text_birthday.setOnClickListener {
            val c: Calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this@Signup3Activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // TODO Auto-generated method stub
                    try {
                        val d: Date = SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                        ).parse(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                        text_birthday.text = d.toString()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )


            datePickerDialog.datePicker.calendarViewShown = false

            datePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

            datePickerDialog.show()
        }






    }
}