package com.uniting.android.Room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.uniting.android.Class.PSDialog
import com.uniting.android.R

class MakeRoom2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_make_room2, container, false)

        var editCategory = rootView.findViewById<EditText>(R.id.edit_category)

        editCategory.setOnClickListener {
            var dialog = PSDialog(activity!!)
        }

        return rootView
    }
}

