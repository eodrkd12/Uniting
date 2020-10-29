package com.uniting.android.Class

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit

class MatchingConditionBottomSheetDialog() : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.dialog_matching_condition, container, false)

        val heightLayout : ConstraintLayout = rootView.findViewById(R.id.layout_matching_height)
        val ageLayout : ConstraintLayout = rootView.findViewById(R.id.layout_matching_age)
        val deptLayout : ConstraintLayout = rootView.findViewById(R.id.layout_matching_dept)
        val hobbyLayout : ConstraintLayout = rootView.findViewById(R.id.layout_matching_hobby)
        val personalityLayout : ConstraintLayout = rootView.findViewById(R.id.layout_matching_personality)

        val heightText : TextView = rootView.findViewById(R.id.text_matching_height)
        val ageText : TextView = rootView.findViewById(R.id.text_matching_age)
        val deptText : TextView = rootView.findViewById(R.id.text_matching_dept)
        val hobbyText : TextView = rootView.findViewById(R.id.text_matching_hobby)
        val personalityText : TextView = rootView.findViewById(R.id.text_matching_personality)

        val saveBtn : Button = rootView.findViewById(R.id.btn_condition_save)

        heightLayout.setOnClickListener(EditConditionOnClickListener(heightText, context as Activity))
        ageLayout.setOnClickListener(EditConditionOnClickListener(ageText, context as Activity))
        deptLayout.setOnClickListener(EditConditionOnClickListener(deptText, context as Activity))
        hobbyLayout.setOnClickListener(EditConditionOnClickListener(hobbyText, context as Activity))
        personalityLayout.setOnClickListener(EditConditionOnClickListener(personalityText, context as Activity))

        heightText.text = MatchingCondition.HEIGHT
        ageText.text = MatchingCondition.AGE
        deptText.text = MatchingCondition.DEPT
        hobbyText.text = MatchingCondition.HOBBY
        personalityText.text = MatchingCondition.PERSONALITY

        saveBtn.setOnClickListener {
            MatchingCondition.AGE = ageText.text.toString()
            MatchingCondition.DEPT = deptText.text.toString()
            MatchingCondition.HEIGHT = heightText.text.toString()
            MatchingCondition.HOBBY = hobbyText.text.toString()
            MatchingCondition.PERSONALITY = personalityText.text.toString()

            Retrofit.updateMatchingCondition {
                if(it.result == "success")
                    Log.d("test", "매칭조건 삽입완료")
            }
            dismiss()
        }
        return rootView
    }
}

class EditConditionOnClickListener(val textView : TextView, val activity : Activity) : View.OnClickListener {
    override fun onClick(p0: View?) {
        var title: String? = null
        var conditionList: ArrayList<String>? = null

        when (p0!!.id) {
            R.id.layout_matching_height -> {
                title = "키를 선택해주세요."
                conditionList = arrayListOf("160cm", "170cm", "180cm")
            }
            R.id.layout_matching_age -> {
                title = "나이를 선택해주세요."
                conditionList = arrayListOf("20", "21", "22")
            }
            R.id.layout_matching_dept -> {
                title = "학과를 선택해주세요."
                conditionList = arrayListOf("컴퓨터공학과", "전자공학과", "기계자동차공학과")
            }
            R.id.layout_matching_hobby -> {
                title = "취미를 선택해주세요."
                conditionList = arrayListOf("축구", "여행", "카페가기")
            }
            R.id.layout_matching_personality -> {
                title = "성격을 선택해주세요."
                conditionList = arrayListOf("착함", "성실함")
            }
        }
        var psDialog = PSDialog(activity)
        psDialog.setMatchingCondition(title!!, conditionList!!, textView)
        psDialog.show()
    }
}

