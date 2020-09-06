package com.uniting.android.Room

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.uniting.android.R

class MakeRoom1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_make_room1, container, false)

        var editTilte = rootView.findViewById<EditText>(R.id.edit_title)

        MakeRoomActivity.textNext!!.isEnabled=false
        MakeRoomActivity.textNext!!.setTextColor(Color.GRAY)

        editTilte.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.length == 0){
                    MakeRoomActivity.textNext!!.isEnabled = false
                    MakeRoomActivity.textNext!!.setTextColor(Color.GRAY)
                }
                else {
                    MakeRoomActivity.textNext!!.isEnabled = true
                    MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                    MakeRoomActivity.title = p0!!.toString()
                }
            }
        })


        return rootView
    }
}