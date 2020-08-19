package com.uniting.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.uniting.android.Item.Test
import com.uniting.android.Singleton.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://52.78.27.41:1901")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitService::class.java)

        service.getData().enqueue(object: Callback<ArrayList<Test.User>> {
            override fun onResponse(call: Call<ArrayList<Test.User>>, response: Response<ArrayList<Test.User>>) {
                if(response.isSuccessful) {
                    var array = response.body()

                    for(i in array!!) {
                        Log.d("test", i.toString()+"\n")
                        Log.d("test", i.userId +", " + i.userPw)
                    }

                }
            }
            override fun onFailure(call: Call<ArrayList<Test.User>>, t: Throwable) {
            }

        })
    }
}