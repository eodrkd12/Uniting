package com.uniting.android.Option

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uniting.android.R

class OptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_option, container, false)

        val modifyUserBtn : TextView = rootView.findViewById(R.id.text_option_modify)

        modifyUserBtn.setOnClickListener {
            var intent = Intent(activity, ModifyActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

}