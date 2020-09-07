package com.uniting.android.Profile

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_profile.*

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

        var displayMetrics: DisplayMetrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        dm = getResources().getDisplayMetrics()
        topPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, dm!!).toInt()
        padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, dm!!).toInt()
        margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, dm!!).toInt()
        layoutMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, dm!!).toInt()

        val userId = intent.getStringExtra("userId")
        text_profile_city.text = intent.getStringExtra("userCity")
        text_profile_dept.text = intent.getStringExtra("deptName")
        text_profile_gender.text = intent.getStringExtra("userGender")
        text_profile_nickname.text = intent.getStringExtra("userNickname")
        text_profile_height.text = intent.getStringExtra("userHeight")
        text_profile_age.text = intent.getStringExtra("userAge")

        var hobby : List<String> = intent.getStringExtra("userHobby")!!.split(",")
        setLayout(layout_hobby, hobby)

        var personality : List<String> = intent.getStringExtra("userPersonality")!!.split(",")
        setLayout(layout_personality, personality)

        /*Retrofit.randomMatching {
            text_profile_city.text = it.get(0).userCity
            text_profile_dept.text = it.get(0).deptName
            text_profile_gender.text = it.get(0).userGender
            text_profile_nickname.text = it.get(0).userNickname
            text_profile_height.text = it.get(0).userHeight
            text_profile_age.text = it.get(0).userAge

            var hobby : List<String> = it.get(0).userHobby.split(",")
            setLayout(layout_hobby, hobby)

            var personality : List<String> = it.get(0).userPersonality.split(",")
            setLayout(layout_personality, personality)
        }*/

    }

    fun setLayout(layout: LinearLayout, textList: List<String>) {
        widthData = 0
        filledWidth = 0
        linearCount = 0
        textViewList.clear()
        linearList.clear()
        for(i in 0..textList.size-2) {
            var view = TextView(this)

            var layoutParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0, 0, margin, 0)
            view.setLayoutParams(layoutParams)

            view.setPadding(padding, topPadding, padding, topPadding)
            view.setBackgroundResource(R.drawable.enable_rounded_button)
            view.setTextColor(Color.WHITE)
            view.setText(textList.get(i))
            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)

            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            widthData += view.measuredWidth + margin

            textViewList.add(view)
        }

        var test = width - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, dm!!).toInt()

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