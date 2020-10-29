package com.uniting.android.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.Class.MatchingCondition
import com.uniting.android.Class.MatchingConditionBottomSheetDialog
import com.uniting.android.Class.PSDialog
import com.uniting.android.Profile.ProfileActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class MatchingFragment() : Fragment() {

    var matchingTypeList = arrayListOf<String>("일반매칭","스마트매칭")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_matching, container, false)

        val vpMatching = rootView.findViewById<ViewPager2>(R.id.vp_matchingtype)
        val matchingAdapter = MatchingTypeAdapter(activity!!, matchingTypeList, fragmentManager!!)
        val btnMatching : Button = rootView.findViewById(R.id.btn_matching)
        val matchingConditionBtn : Button = rootView.findViewById(R.id.btn_matching_condition)

        matchingConditionBtn.setOnClickListener {
            val psDialog = MatchingConditionBottomSheetDialog()
            psDialog.show(activity!!.supportFragmentManager, psDialog.tag)
        }

        vpMatching.adapter=matchingAdapter

        vpMatching.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        vpMatching.currentItem=0

        vpMatching.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        val parent : ViewGroup = rootView.findViewById(R.id.parent)
                        val transition : Transition = Slide(Gravity.RIGHT)
                        transition.addTarget(R.id.btn_matching_condition)
                        transition.duration = 400
                        androidx.transition.TransitionManager.beginDelayedTransition(parent, transition)
                        matchingConditionBtn.visibility = View.INVISIBLE
                    }
                    1 -> {
                       val parent : ViewGroup = rootView.findViewById(R.id.parent)
                        val transition : Transition = Slide(Gravity.RIGHT)
                        transition.addTarget(R.id.btn_matching_condition)
                        transition.duration = 400
                        androidx.transition.TransitionManager.beginDelayedTransition(parent, transition)
                        matchingConditionBtn.visibility = View.VISIBLE
                    }
                }

            }
        })

        btnMatching.setOnClickListener {
            when(vpMatching.currentItem) {
                0 -> {
                    Retrofit.randomMatching {
                        if(it.size == 0) {
                            Toast.makeText(activity, "매칭 가능한 상대가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            var intent = Intent(activity, ProfileActivity::class.java)
                            intent.putExtra("userId", it.get(0).userId)
                            intent.putExtra("userCity", it.get(0).userCity)
                            intent.putExtra("deptName", it.get(0).deptName)
                            intent.putExtra("userGender", it.get(0).userGender)
                            intent.putExtra("userNickname", it.get(0).userNickname)
                            intent.putExtra("userHeight", it.get(0).userHeight)
                            intent.putExtra("userAge", it.get(0).userAge)
                            intent.putExtra("userHobby", it.get(0).userHobby)
                            intent.putExtra("userPersonality", it.get(0).userPersonality)
                            startActivity(intent)
                        }
                    }
                }
                1 -> {
                    if(MatchingCondition.HEIGHT == "" || MatchingCondition.AGE == "") {
                        Toast.makeText(activity, "매칭조건을 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Retrofit.smartMatching(MatchingCondition.HEIGHT, MatchingCondition.AGE, MatchingCondition.DEPT, MatchingCondition.HOBBY, MatchingCondition.PERSONALITY) {
                            if(it.size == 0) {
                                Toast.makeText(activity, "매칭 가능한 상대가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Log.d("test", "테스트")
                                var intent = Intent(activity, ProfileActivity::class.java)
                                intent.putExtra("userId", it.get(0).userId)
                                intent.putExtra("userCity", it.get(0).userCity)
                                intent.putExtra("deptName", it.get(0).deptName)
                                intent.putExtra("userGender", it.get(0).userGender)
                                intent.putExtra("userNickname", it.get(0).userNickname)
                                intent.putExtra("userHeight", it.get(0).userHeight)
                                intent.putExtra("userAge", it.get(0).userAge)
                                intent.putExtra("userHobby", it.get(0).userHobby)
                                intent.putExtra("userPersonality", it.get(0).userPersonality)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
        return rootView
    }
}