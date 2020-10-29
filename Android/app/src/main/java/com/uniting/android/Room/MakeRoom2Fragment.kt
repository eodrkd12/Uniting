package com.uniting.android.Room

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.uniting.android.Class.PSDialog
import com.uniting.android.R

class MakeRoom2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_make_room2, container, false)

        var textCategory = rootView.findViewById<TextView>(R.id.text_category)

        Log.d("test",MakeRoomActivity.title)

        MakeRoomActivity.textNext!!.isEnabled=false
        MakeRoomActivity.textNext!!.setTextColor(Color.GRAY)

        textCategory.setOnClickListener {
            var dialog = PSDialog.BottomSheetDialog(textCategory)
            dialog.show(activity!!.supportFragmentManager, dialog.tag)
        }

        return rootView
    }
}

