package com.uniting.android.Cafeteria

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Class.UserInfo
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_signup3.*
import kotlinx.android.synthetic.main.fragment_cafeteria.*

class CafeteriaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cafeteria, container, false)
        val verticalCafeteriaRV : RecyclerView = rootView.findViewById(R.id.rv_verticalcafeteria)
        val univName : TextView = rootView.findViewById(R.id.text_cafeteria_univ_name)

        val campusSpinner : Spinner = rootView.findViewById(R.id.spinner_campus)

        univName.text = UserInfo.UNIV
        var campusList = arrayListOf("성서", "대명")

        val adapter = ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, campusList)
        campusSpinner.adapter = adapter
        campusSpinner.setSelection(0)
        campusSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Retrofit.getCafeteriaList(campusSpinner.getItemAtPosition(p2).toString()) {
                    val cafeteriaList = arrayListOf(it.koreanFoodList, it.chineseFoodList, it.westernFoodList, it.japaneseFoodList, it.fastFoodList)

                    verticalCafeteriaRV.setHasFixedSize(true)
                    verticalCafeteriaRV.layoutManager = LinearLayoutManager(activity)
                    verticalCafeteriaRV.adapter = CafeteriaVerticalAdapter(activity!!, cafeteriaList)
                }
            }
        }

        return rootView
    }
}
