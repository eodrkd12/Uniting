package com.uniting.android.Cafeteria

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Class.UserInfo
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class CafeteriaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cafeteria, container, false)
        val verticalCafeteriaRV : RecyclerView = rootView.findViewById(R.id.rv_verticalcafeteria)
        val univName : TextView = rootView.findViewById(R.id.text_cafeteria_univ_name)

        univName.text = UserInfo.UNIV

        Retrofit.getCafeteriaList {
            Log.d("test", "식당목록 호출")
            val cafeteriaList = arrayListOf(it.koreanFoodList, it.chineseFoodList, it.westernFoodList, it.japaneseFoodList, it.chickenFoodList)

            verticalCafeteriaRV.setHasFixedSize(true)
            verticalCafeteriaRV.layoutManager = LinearLayoutManager(activity)
            verticalCafeteriaRV.adapter = CafeteriaVerticalAdapter(activity!!, cafeteriaList)
        }

        return rootView
    }
}
