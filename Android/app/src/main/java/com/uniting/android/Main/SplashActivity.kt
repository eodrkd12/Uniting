package com.uniting.android.Main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.uniting.android.Class.MatchingCondition
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.PSDialog
import com.uniting.android.Class.UserInfo
import com.uniting.android.Login.LoginActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class SplashActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        UserInfo.ID = userPref.getString("ID", "")!!
        UserInfo.PW = userPref.getString("PW", "")!!

        Retrofit.getVersionInfo {
            if ((getVersionInfo().toDouble() < it.appVersion.toDouble()) && it.updateIssue == "essential") {
                val psDialog = PSDialog(this)
                psDialog.setUpdateDialog()
                psDialog.show()
            } else {
                if(UserInfo.ID != "") {
                    Retrofit.login(UserInfo.ID, UserInfo.PW) {
                        when(it.result) {
                            "success" -> {
                                UserInfo.ID = it.userId
                                UserInfo.PW = it.userPw
                                UserInfo.GENDER = it.userGender
                                UserInfo.NICKNAME = it.userNickname
                                UserInfo.BLOCKINGDEPT = it.blockingDept
                                UserInfo.UNIV = it.univName
                                UserInfo.DEPT = it.deptName

                                MatchingCondition.AGE = it.matchingAge
                                MatchingCondition.DEPT = it.matchingDept
                                MatchingCondition.HEIGHT = it.matchingHeight
                                MatchingCondition.PERSONALITY = it.matchingPersonality
                                MatchingCondition.HOBBY = it.matchingHobby

                                val pref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                                val editor = pref.edit()
                                editor.putString("ID", UserInfo.ID)
                                editor.putString("PW", UserInfo.PW)
                                    .apply()

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()

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
                            "fail" -> {
                                val intent= Intent(this, LoginActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                                startActivity(intent)
                                finish()
                            }
                            "blacklist" -> {
                                val intent= Intent(this, LoginActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
                else {
                    Handler().postDelayed({
                        val intent= Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                        startActivity(intent)
                        finish()
                    }, 2000)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val notificationChannel = NotificationChannel(
                        "fcm",
                        "fcm",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationChannel.description = "fcm channel"
                    notificationChannel.enableLights(true)
                    notificationChannel.lightColor = Color.GREEN
                    notificationChannel.enableVibration(true)
                    notificationChannel.vibrationPattern = longArrayOf(100, 200, 100, 200)
                    notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                    notificationChannel.setShowBadge(true)
                    notificationChannel.enableVibration(true)
                    notificationManager.createNotificationChannel(notificationChannel)
                }
            }
        }

    }
}