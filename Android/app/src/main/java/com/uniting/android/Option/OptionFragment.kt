package com.uniting.android.Option

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uniting.android.Class.UserInfo
import com.uniting.android.Login.LoginActivity
import com.uniting.android.R

class OptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_option, container, false)

        val modifyUserBtn : TextView = rootView.findViewById(R.id.text_option_modify)
        val logoutBtn : TextView = rootView.findViewById(R.id.text_option_logout)

        modifyUserBtn.setOnClickListener {
            var intent = Intent(activity, ModifyActivity::class.java)
            startActivity(intent)
        }

        logoutBtn.setOnClickListener {
            var userPref = activity!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            var editor = userPref.edit()
            editor.clear()
            UserInfo.ID= ""
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

}