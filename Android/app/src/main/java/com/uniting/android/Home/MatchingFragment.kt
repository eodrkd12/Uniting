package com.uniting.android.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.Class.PSDialog
import com.uniting.android.Profile.ProfileActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class MatchingFragment : Fragment() {

    var matchingTypeList = arrayListOf<String>("일반매칭","스마트매칭")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_matching, container, false)

        val vpMatching = rootView.findViewById<ViewPager2>(R.id.vp_matchingtype)
        val matchingAdapter = MatchingTypeAdapter(activity!!, matchingTypeList)
        val btnMatching : Button = rootView.findViewById(R.id.btn_matching)

        vpMatching.adapter=matchingAdapter

        vpMatching.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        vpMatching.currentItem=0

        val matchingHeight : ConstraintLayout = rootView.findViewById(R.id.layout_matching_height)
        val matchingAge : ConstraintLayout = rootView.findViewById(R.id.layout_matching_age)
        val matchingDept : ConstraintLayout = rootView.findViewById(R.id.layout_matching_dept)
        val matchingHobby : ConstraintLayout = rootView.findViewById(R.id.layout_matching_hobby)
        val matchingPersonality : ConstraintLayout = rootView.findViewById(R.id.layout_matching_personality)
        
        val heightText : TextView = rootView.findViewById(R.id.text_matching_height)
        val ageText : TextView = rootView.findViewById(R.id.text_matching_age)
        val deptText : TextView = rootView.findViewById(R.id.text_matching_dept)
        val hobbyText : TextView = rootView.findViewById(R.id.text_matching_hobby)
        val personalityText : TextView = rootView.findViewById(R.id.text_matching_personality)

        matchingHeight.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching, heightText))
        matchingAge.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching, ageText))
        matchingDept.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching, deptText))
        matchingHobby.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching, hobbyText))
        matchingPersonality.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching, personalityText))

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
                    if(heightText.text == "" || ageText.text == "") {
                        Toast.makeText(activity, "매칭조건을 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        var height = heightText.text.toString()
                        var age = ageText.text.toString()
                        var department = deptText.text.toString()
                        var hobby = hobbyText.text.toString()
                        var personality = personalityText.text.toString()

                        Retrofit.smartMatching(height,age,department,hobby,personality) {
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
                }
            }
        }

        return rootView
    }

    class EditConditionOnClickListener(val activity: FragmentActivity, val vpPager : ViewPager2, val textView : TextView) : View.OnClickListener {
        override fun onClick(p0: View?) {
            if(vpPager.currentItem == 0){
                var psDialog = PSDialog(activity)
                psDialog.setMatchingChange(vpPager)
                psDialog.show()
            }
            else {
                var title : String? = null
                var conditionList : ArrayList<String>? = null

                when(p0!!.id){
                    R.id.layout_matching_height -> {
                        title = "키를 선택해주세요."
                        conditionList = arrayListOf("160cm","170cm","180cm")
                    }
                    R.id.layout_matching_age -> {
                        title = "나이를 선택해주세요."
                        conditionList = arrayListOf("20","21","22")
                    }
                    R.id.layout_matching_dept -> {
                        title = "학과를 선택해주세요."
                        conditionList = arrayListOf("컴퓨터공학과","전자공학과","기계자동차공학과")
                    }
                    R.id.layout_matching_hobby -> {
                        title = "취미를 선택해주세요."
                        conditionList = arrayListOf("축구","여행","카페가기")
                    }
                    R.id.layout_matching_personality -> {
                        title = "성격을 선택해주세요."
                        conditionList = arrayListOf("착함","성실함")
                    }
                }
                var psDialog = PSDialog(activity)
                psDialog.setMatchingCondition(title!!, conditionList!!, textView)
                psDialog.show()
            }

        }
    }
}