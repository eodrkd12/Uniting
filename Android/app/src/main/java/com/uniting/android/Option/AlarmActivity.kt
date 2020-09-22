package com.uniting.android.Option

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        btn_alarm_back.setOnClickListener {
            finish()
        }
    }
}