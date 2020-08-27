package com.uniting.android.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.R

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

        vpCategory.adapter=categoryAdapter

        vpCategory.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        vpCategory.currentItem=0

        return rootView
    }
}