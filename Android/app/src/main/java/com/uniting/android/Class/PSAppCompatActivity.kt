package com.uniting.android.Class

import androidx.appcompat.app.AppCompatActivity
import com.uniting.android.Interface.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

abstract class PSAppCompatActivity : AppCompatActivity(){

    fun getCurDate() : String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())

        return curDate
    }
}