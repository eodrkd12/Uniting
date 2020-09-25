package com.uniting.android.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.uniting.android.Cafeteria.CafeteriaFragment
import com.uniting.android.Class.UserInfo
import com.uniting.android.Room.MyRoomFragment
import com.uniting.android.Home.HomeFragment
import com.uniting.android.Option.OptionFragment
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_main.*

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

        //임시로 유저데이터 불러오기
        Retrofit.getModifyUserInfo(intent.getStringExtra("id")!!) {
            UserInfo.ID = intent.getStringExtra("id")!!
            UserInfo.UNIV = it.univName
            UserInfo.DEPT = it.deptName
            UserInfo.NICKNAME = it.userNickname
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

                it.setIcon(R.drawable.home_click_icon)

                bnv_main.menu.findItem(R.id.menu_chat)


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

                bnv_main.menu.findItem(R.id.menu_home).setIcon(R.drawable.home_icon)

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

                bnv_main.menu.findItem(R.id.menu_home).setIcon(R.drawable.home_icon)

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

                bnv_main.menu.findItem(R.id.menu_home).setIcon(R.drawable.home_icon)


                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}