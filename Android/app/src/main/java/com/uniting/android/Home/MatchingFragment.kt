package com.uniting.android.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.R

class MatchingFragment : Fragment() {

    var matchingTypeList = arrayListOf<String>("일반매칭","스마트매칭")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_matching, container, false)

        var vpMatching = rootView.findViewById<ViewPager2>(R.id.vp_matchingtype)
        var matchingAdapter = MatchingTypeAdapter(activity!!, matchingTypeList)

        vpMatching.adapter=matchingAdapter

        vpMatching.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        vpMatching.currentItem=0

        var layoutCondition = rootView.findViewById<ConstraintLayout>(R.id.layout_condition)

        vpMatching.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                Log.d("test",position.toString())
                when(position){
                    0 -> {
                        layoutCondition.visibility=View.GONE
                    }
                    1 -> {
                        layoutCondition.visibility=View.VISIBLE
                    }
                }
            }
        })

        return rootView
    }
}