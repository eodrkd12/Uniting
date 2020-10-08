package com.uniting.android.Option

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.uniting.android.Class.PSDialog
import com.uniting.android.Class.UserInfo
import com.uniting.android.Login.LoginActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.fragment_option.*

class OptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_option, container, false)

        val psDialog = PSDialog(activity!!)

        val modifyBtn : Button = rootView.findViewById(R.id.btn_option_modify)
        val logoutBtn : TextView = rootView.findViewById(R.id.text_option_logout)
        val blockingBtn : Switch = rootView.findViewById(R.id.switch_blocking)
        val alarmBtn : TextView = rootView.findViewById(R.id.text_option_alarm)
        val inquireBtn : TextView = rootView.findViewById(R.id.text_option_inquire)

        if(UserInfo.BLOCKINGDEPT == 1) {
            blockingBtn.isChecked = true
        }
        else {
            blockingBtn.isChecked = false
        }

        modifyBtn.setOnClickListener {
            var intent = Intent(activity, ModifyActivity::class.java)
            startActivity(intent)
        }

        logoutBtn.setOnClickListener {
            var userPref = activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            var editor = userPref.edit()
            editor.clear().apply()
            UserInfo.ID = ""
            UserInfo.PW = ""
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }

        blockingBtn.setOnCheckedChangeListener { compoundButton, b ->
            when(b) {
                true -> {
                    psDialog.setBlocking(blockingBtn)
                    psDialog.show()
                }
                false -> {
                    Retrofit.updateBlocking(UserInfo.ID, null, null, "delete") {
                        if(it.result == "success") {
                            UserInfo.BLOCKINGDEPT = 0
                            Log.d("test", "학과차단해제완료")
                        }
                        else {
                            Log.d("test", "학과차단해제오류")
                            blockingBtn.isChecked = true
                        }
                    }
                }
            }
        }

        alarmBtn.setOnClickListener {
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        inquireBtn.setOnClickListener {
            val intent = Intent(activity, InquireActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

}