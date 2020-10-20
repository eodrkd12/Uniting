package com.uniting.android.Room

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.uniting.android.Chat.ChatActivity
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.UserInfo
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_make_room.*
import java.time.DayOfWeek
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
                                        UserInfo.ID,
                                        MakeRoomActivity.category,
                                        date,
                                        MakeRoomActivity.introduce,
                                        UserInfo.UNIV
                                    )

                                    val calendar = Calendar.getInstance()
                                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                                    var dayOfWeekStr = ""

                                    when(dayOfWeek) {
                                        1 -> {
                                            dayOfWeekStr = "일요일"
                                        }
                                        2 -> {
                                            dayOfWeekStr = "월요일"
                                        }
                                        3 -> {
                                            dayOfWeekStr = "화요일"
                                        }
                                        4 -> {
                                            dayOfWeekStr = "수요일"
                                        }
                                        5 -> {
                                            dayOfWeekStr = "목요일"
                                        }
                                        6 -> {
                                            dayOfWeekStr = "금요일"
                                        }
                                        7 -> {
                                            dayOfWeekStr = "토요일"
                                       }
                                    }

                                    var systemChat = Chat(
                                        "SYSTEM_${room.room_id}_${date}",
                                        room.room_id,
                                        "SYSTEM",
                                        "SYSTEM",
                                        "${getCurDate().split(" ")[0]} ${dayOfWeekStr}",
                                        date, "", 1
                                    )

                                    var chatViewModel = ChatViewModel(application, roomId)

                                    Retrofit.insertChat(systemChat){
                                        if(it.result == "success"){
                                            writeFirebase(systemChat, roomId)
                                            chatViewModel.insert(systemChat){
                                                var intent = Intent(this, ChatActivity::class.java)
                                                intent.putExtra("room",room)
                                                intent.putExtra("last_chat_time","0000-00-00")
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
        }
    }
    fun writeFirebase(chat: Chat, roomId: String){

        var ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomId)

        var map = HashMap<String, Any>()
        val key: String? = ref!!.push().key

        var root = ref!!.child(key!!)
        var objectMap = HashMap<String, Any>()

        objectMap.put("chat_id", chat.chat_id)
        objectMap.put("room_id", chat.room_id)
        objectMap.put("user_id", chat.user_id)
        objectMap.put("user_nickname", chat.user_nickname)
        objectMap.put("chat_content", chat.chat_content)
        objectMap.put("chat_time", chat.chat_time)
        objectMap.put("unread_member", chat.unread_member)
        objectMap.put("system_chat", chat.system_chat)

        root.updateChildren(objectMap)
    }

}