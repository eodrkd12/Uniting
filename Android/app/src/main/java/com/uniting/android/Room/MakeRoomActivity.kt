package com.uniting.android.Room

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.uniting.android.Chat.ChatActivity
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Room
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_make_room.*
import java.util.*
import kotlin.random.Random.Default.nextInt

class MakeRoomActivity : PSAppCompatActivity() {

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

                    var date = this.getCurDate()

                    var roomId = ""
                    for(i in 0..11){
                        roomId += Random().nextInt(10).toString()
                    }

                    Retrofit.createRoom(roomId,MakeRoomActivity.title, category, date, introduce,"${UserInfo.UNIV}","${UserInfo.ID}"){
                        if(it.result=="success"){
                            Retrofit.joinRoom(roomId, UserInfo.ID, date){
                                if(it.result=="success"){
                                    var room = Room(
                                        roomId,
                                        MakeRoomActivity.title,
                                        MakeRoomActivity.category,
                                        date,
                                        MakeRoomActivity.introduce,
                                        UserInfo.UNIV,
                                        UserInfo.ID
                                    )

                                    var intent = Intent(this, ChatActivity::class.java)
                                    intent.putExtra("room",room)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}