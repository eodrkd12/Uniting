package com.uniting.android.Cafeteria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class CafeteriaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cafeteria, container, false)
        val verticalCafeteriaRV : RecyclerView = rootView.findViewById(R.id.rv_verticalcafeteria)

        verticalCafeteriaRV.setHasFixedSize(true)
        verticalCafeteriaRV.layoutManager = LinearLayoutManager(activity)




        return rootView
    }
}