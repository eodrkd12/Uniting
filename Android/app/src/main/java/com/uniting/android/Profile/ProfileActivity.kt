package com.uniting.android.Profile

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.uniting.android.Class.PSDialog
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.SimpleDateFormat

class ProfileActivity : AppCompatActivity() {

    var textViewList = ArrayList<TextView>()
    var linearList = ArrayList<LinearLayout>()
    var widthData : Int = 0
    var width : Int = 0
    var linearCount : Int = 0
    var filledWidth : Int = 0
    var dm : DisplayMetrics? = null
    var topPadding = 0
    var padding = 0
    var margin = 0
    var layoutMargin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())

        var age = curDate.substring(0, 4).toInt() - intent.getStringExtra("userAge")!!.substring(0, 4).toInt() + 1

        this.window.statusBarColor = Color.parseColor("#00BFFF")

        var displayMetrics: DisplayMetrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        dm = getResources().getDisplayMetrics()
        layoutMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, dm!!).toInt()
        margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, dm!!).toInt()

        val userId = intent.getStringExtra("userId")


        var height : String? = null
        var hobby : String? = null
        var personality : String? = null

        height = intent.getStringExtra("userHeight")
        hobby = intent.getStringExtra("userHobby")
        personality = intent.getStringExtra("userPersonality")

        text_profile_city.text = intent?.getStringExtra("userCity")
        text_profile_dept.text = intent.getStringExtra("deptName")
        text_profile_nickname.text = intent.getStringExtra("userNickname")
        text_profile_age.text = age.toString() + "세"

        if(height == null) {
            textView5.visibility = View.GONE
            text_profile_height.visibility = View.GONE
        } else {
            text_profile_height.text = height
        }

        if(hobby == null) {
            textView12.visibility = View.GONE
            layout_hobby.visibility = View.GONE
        } else {
            setLayout(layout_hobby, hobby.split(","))
        }

        if(personality == null) {
            textView13.visibility = View.GONE
            layout_personality.visibility = View.GONE
        } else {
            setLayout(layout_personality, personality.split(","))
        }

        image_profile_back.setOnClickListener {
            finish()
        }

        btn_chat.setOnClickListener {
            val psDialog = PSDialog(this)
            psDialog.setChat(userId!!, text_profile_nickname.text.toString())
            psDialog.show()
        }
    }

    fun setLayout(layout: LinearLayout, textList: List<String>) {
        widthData = 0
        filledWidth = 0
        linearCount = 0
        textViewList.clear()
        linearList.clear()
        for(i in 0..textList.size-1) {
            var view = TextView(this)

            var layoutParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0, 0, margin, 0)
            view.setLayoutParams(layoutParams)

            //view.setPadding(padding, topPadding, padding, topPadding)
            view.setBackgroundResource(R.drawable.testdrawable)
            view.setTextColor(Color.WHITE)
            view.setText(textList.get(i))
            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)


            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            widthData += view.measuredWidth + margin

            textViewList.add(view)
        }

        var test = width - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45f, dm!!).toInt()

        if(test < widthData) {
            for(i in 0..widthData/test) {
                var linear : LinearLayout = LinearLayout(this)
                linear.setOrientation(LinearLayout.HORIZONTAL)
                var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 0, 0, layoutMargin)
                linear.setLayoutParams(params)
                linearList.add(linear)
            }
        }
        else {
            var linear : LinearLayout = LinearLayout(this)
            linear.setOrientation(LinearLayout.HORIZONTAL)
            var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 0, layoutMargin)
            linear.setLayoutParams(params)
            linearList.add(linear)
        }


        for(i in 0..textViewList.size-1) {
            textViewList.get(i).measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            filledWidth += (textViewList.get(i).measuredWidth + margin)

            if(linearCount < linearList.size && filledWidth < test) {
                linearList.get(linearCount).addView(textViewList.get(i))
            }
            else {
                filledWidth = textViewList.get(i).measuredWidth + margin
                linearCount++
                linearList.get(linearCount).addView(textViewList.get(i))
            }
        }

        for(i in 0..linearList.size-1) {
            layout.addView(linearList.get(i))
        }

    }
}