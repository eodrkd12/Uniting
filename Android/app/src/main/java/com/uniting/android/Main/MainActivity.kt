package com.uniting.android.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uniting.android.Cafeteria.CafeteriaFragment
import com.uniting.android.Room.MyRoomFragment
import com.uniting.android.Home.HomeFragment
import com.uniting.android.Item.Test
import com.uniting.android.Option.OptionFragment
import com.uniting.android.R
import com.uniting.android.Interface.RetrofitService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var homeFragment : HomeFragment? = null
    var chatFragment : MyRoomFragment? = null
    var cafeteriaFragment : CafeteriaFragment? = null
    var optionFragment : OptionFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().add(R.id.frame_main, homeFragment!!).commit()
        }

        bnv_main.setOnNavigationItemSelectedListener(navListener)

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

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.menu_home -> {
                if(homeFragment == null) {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, homeFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().show(homeFragment!!).commit()
                if(chatFragment != null) supportFragmentManager.beginTransaction().hide(chatFragment!!).commit()
                if(cafeteriaFragment != null) supportFragmentManager.beginTransaction().hide(cafeteriaFragment!!).commit()
                if(optionFragment != null) supportFragmentManager.beginTransaction().hide(optionFragment!!).commit()


                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_chat -> {
                if(chatFragment == null) {
                    chatFragment = MyRoomFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, chatFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(chatFragment != null) supportFragmentManager.beginTransaction().show(chatFragment!!).commit()
                if(cafeteriaFragment != null) supportFragmentManager.beginTransaction().hide(cafeteriaFragment!!).commit()
                if(optionFragment != null) supportFragmentManager.beginTransaction().hide(optionFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_cafeteria -> {
                if(cafeteriaFragment == null) {
                    cafeteriaFragment = CafeteriaFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, cafeteriaFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(chatFragment != null) supportFragmentManager.beginTransaction().hide(chatFragment!!).commit()
                if(cafeteriaFragment != null) supportFragmentManager.beginTransaction().show(cafeteriaFragment!!).commit()
                if(optionFragment != null) supportFragmentManager.beginTransaction().hide(optionFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_option -> {
                if(optionFragment == null) {
                    optionFragment = OptionFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, optionFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(chatFragment != null) supportFragmentManager.beginTransaction().hide(chatFragment!!).commit()
                if(cafeteriaFragment != null) supportFragmentManager.beginTransaction().hide(cafeteriaFragment!!).commit()
                if(optionFragment != null) supportFragmentManager.beginTransaction().show(optionFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}