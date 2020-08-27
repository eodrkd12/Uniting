package com.uniting.android.Room

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_make_room.*

class MakeRoomActivity : AppCompatActivity() {

    companion object{
        var current = 0
        var textNext : TextView? = null

        var title = ""
        var category = ""
        var introduce = ""
    }

    lateinit var firstFragment: MakeRoom1Fragment
    lateinit var secondFragment: MakeRoom2Fragment
    lateinit var thirdFragment: MakeRoom3Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_room)

        //var window = this.window
        //window.statusBarColor= Color.parseColor("#D5212121")

        textNext = text_next

        textNext!!.isEnabled = false

        if(savedInstanceState == null){
            firstFragment = MakeRoom1Fragment()
            secondFragment = MakeRoom2Fragment()
            thirdFragment = MakeRoom3Fragment()

            current = 1

            supportFragmentManager.beginTransaction().add(R.id.frame_makeroom, firstFragment).commit()
        }

        text_cancel.setOnClickListener {
            when(current) {
                1 -> {
                    finish()
                }
                2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_makeroom, firstFragment).commit()
                    current--
                }
                3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_makeroom, secondFragment).commit()
                    current--
                }
            }
        }

        text_next.setOnClickListener {
            when(current){
                1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_makeroom, secondFragment).commit()
                    current++
                }
                2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_makeroom, thirdFragment).commit()
                    current++
                }
                3 -> {
                    //채팅방으로 화면 전환
                }
            }
        }
    }
}