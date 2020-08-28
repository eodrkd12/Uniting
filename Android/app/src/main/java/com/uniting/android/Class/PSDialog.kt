package com.uniting.android.Class

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.R
import java.text.SimpleDateFormat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uniting.android.Home.ConditionAdapter
import com.uniting.android.Room.MakeRoomActivity

class PSDialog(activity: Activity) {

    var context : Activity? = null
    var dialog : Dialog? = null

    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var curDate = simpleDateFormat.format(System.currentTimeMillis())

    init {
        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        context =activity
    }

    fun show() {
        if(dialog != null) {
            dialog!!.show()
        }
    }

    fun dismiss() {
        if(dialog != null) {
            dialog!!.dismiss()
        }
    }

    fun setMatchingChange(vpMatching : ViewPager2) {
        dialog!!.setContentView(R.layout.dialog_matching_change)

        var layoutBackground = dialog!!.findViewById<ConstraintLayout>(R.id.layout_background)
        var btnCancel = dialog!!.findViewById<Button>(R.id.btn_cancel)
        var btnChange = dialog!!.findViewById<Button>(R.id.btn_change)

        layoutBackground.setOnClickListener {
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnChange.setOnClickListener {
            vpMatching.currentItem=1
            dismiss()
        }
    }

    fun setMatchingCondition(title : String, conditionList: ArrayList<String>, textView: TextView){
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        var view = context!!.layoutInflater.inflate(R.layout.dialog_edit_condition, null)

        var textTitle = view.findViewById<TextView>(R.id.layout_room)
        var rvCondition = view.findViewById<RecyclerView>(R.id.rv_condition)
        var btnSave = view.findViewById<Button>(R.id.btn_save)

        textTitle.setText(title)
        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(view, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        rvCondition.setHasFixedSize(true)
        rvCondition.layoutManager=LinearLayoutManager(context!!,RecyclerView.VERTICAL,false)
        rvCondition.adapter=ConditionAdapter(context!!, conditionList)

        btnSave.setOnClickListener {
            textView.setText(ConditionAdapter.selectedCondition)
            dismiss()
        }
    }

    class BottomSheetDialog() : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.dialog_category, container, false)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            view?.findViewById<TextView>(R.id.text_hobby_tag)?.setOnClickListener {
                MakeRoomActivity.category="취미"
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_study)?.setOnClickListener {
                MakeRoomActivity.category="스터디"
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_counsel)?.setOnClickListener {
                MakeRoomActivity.category="고민상담"
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_talk)?.setOnClickListener {
                MakeRoomActivity.category="잡담"
                dismiss()
            }
        }
    }
}