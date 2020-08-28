package com.uniting.android.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.Class.PSDialog
import com.uniting.android.Profile.ProfileActivity
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
        var btnMatching : Button = rootView.findViewById(R.id.btn_matching)

        vpMatching.adapter=matchingAdapter

        vpMatching.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        vpMatching.currentItem=0

        var textHeight = rootView.findViewById<TextView>(R.id.text_height)
        var textAge = rootView.findViewById<TextView>(R.id.text_age)
        var textDepartment = rootView.findViewById<TextView>(R.id.text_department)
        var textHobby = rootView.findViewById<TextView>(R.id.text_hobby)
        var textPersonality = rootView.findViewById<TextView>(R.id.text_personality)

        textHeight.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching))
        textAge.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching))
        textDepartment.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching))
        textHobby.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching))
        textPersonality.setOnClickListener(EditConditionOnClickListener(activity!!,vpMatching))

        btnMatching.setOnClickListener {
            var intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    class EditConditionOnClickListener(var activity: FragmentActivity, var vpPager : ViewPager2) : View.OnClickListener {
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
                    R.id.text_height -> {
                        title = "키를 선택해주세요."
                        conditionList = arrayListOf("160cm","170cm","180cm")
                    }
                    R.id.text_age -> {
                        title = "나이를 선택해주세요."
                        conditionList = arrayListOf("20","21","22")
                    }
                    R.id.text_department -> {
                        title = "학과를 선택해주세요."
                        conditionList = arrayListOf("컴퓨터공학과","전자공학과","기계자동차공학과")
                    }
                    R.id.text_hobby -> {
                        title = "취미를 선택해주세요."
                        conditionList = arrayListOf("축구","여행","카페가기")
                    }
                    R.id.text_personality -> {
                        title = "성격를 선택해주세요."
                        conditionList = arrayListOf("착함","성실함")
                    }
                }
                var psDialog = PSDialog(activity)
                psDialog.setMatchingCondition(title!!, conditionList!!,p0 as TextView)
                psDialog.show()
            }

        }
    }
}