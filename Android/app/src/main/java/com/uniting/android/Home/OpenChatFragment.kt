package com.uniting.android.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.R
import com.uniting.android.Room.RoomListActivity

class OpenChatFragment : Fragment() {

    var categoryList = arrayListOf<String>("취미","스터디","고민상담","잡담")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_open_chat, container, false)

        var vpCategory = rootView.findViewById<ViewPager2>(R.id.vp_category)
        var categoryAdapter = CategoryAdapter(activity!!,categoryList)
        var btnConnect = rootView.findViewById<Button>(R.id.btn_connect)

        vpCategory.adapter=categoryAdapter

        vpCategory.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        vpCategory.currentItem=0

        btnConnect.setOnClickListener {
            var intent = Intent(activity,RoomListActivity::class.java)
            when(vpCategory.currentItem){
                0 -> {
                    intent.putExtra("category","취미")
                }
                1 -> {
                    intent.putExtra("category","스터디")
                }
                2 -> {
                    intent.putExtra("category","고민상담")
                }
                3 -> {
                    intent.putExtra("category","잡담")
                }
            }
            startActivity(intent)
        }


        return rootView
    }
}