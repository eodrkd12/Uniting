package com.uniting.android.Room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.uniting.android.R

class MakeRoom1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_make_room1, container, false)

        var editTilte = rootView.findViewById<EditText>(R.id.edit_title)

        editTilte.addTextChangedListener {
            if(it!!.length==0){

            }
        }

        return rootView
    }
}