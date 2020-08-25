package com.uniting.android.Room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_make_room.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MakeRoomActivity : AppCompatActivity() {

    companion object{
        var CURRENT = 0
        var TEXT_NEXT : TextView? = null
    }

    lateinit var firstFragment: MakeRoom1Fragment
    lateinit var secondFragment: MakeRoom2Fragment
    lateinit var thirdFragment: MakeRoom3Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_room)

        TEXT_NEXT = text_next

        if(savedInstanceState == null){
            firstFragment = MakeRoom1Fragment()
            secondFragment = MakeRoom2Fragment()
            thirdFragment = MakeRoom3Fragment()

            CURRENT = 1

            supportFragmentManager.beginTransaction().add(R.id.frame_makeroom, firstFragment).commit()
        }

        text_cancel.setOnClickListener {
            when(CURRENT) {
                1 -> {
                    finish()
                }
                2 -> {
                    supportFragmentManager.beginTransaction().add(R.id.frame_makeroom, firstFragment).commit()
                }
                3 -> {
                    supportFragmentManager.beginTransaction().add(R.id.frame_makeroom, secondFragment).commit()
                }
            }
        }

        text_next.setOnClickListener {
            when(CURRENT){
                1 -> {
                    supportFragmentManager.beginTransaction().add(R.id.frame_makeroom, secondFragment).commit()
                }
                2 -> {
                    supportFragmentManager.beginTransaction().add(R.id.frame_makeroom, thirdFragment).commit()
                }
                3 -> {

                }
            }
        }
    }
}