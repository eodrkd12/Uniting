package com.uniting.android.Class

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
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
import com.uniting.android.Login.DepartmentAdapter
import com.uniting.android.Login.UserItem
import com.uniting.android.Login.UserOptionAdapter
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

    interface SaveBtnClickListener {
        fun onClick(userOption : String)
    }

    private lateinit var saveBtnClickListener : SaveBtnClickListener

    fun setSaveBtnClickListener(saveBtnClickListener: SaveBtnClickListener) {
        this.saveBtnClickListener = saveBtnClickListener
    }

    fun setUserOption(title : String, userOptionList: ArrayList<UserItem.UserOption>) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_edit_condition, null)
        var titleText : TextView = dialogView.findViewById(R.id.text_condition_title)
        var userOptionRV: RecyclerView = dialogView.findViewById(R.id.rv_condition)
        var selectedOption = ""
        var saveBtn: Button = dialogView.findViewById(R.id.btn_save)

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))


        var ssb: SpannableStringBuilder = SpannableStringBuilder(title + "을 알려주세요.")
        ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleText.text = ssb

        userOptionRV.setHasFixedSize(true)
        userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        userOptionRV.adapter = UserOptionAdapter(context!!, userOptionList)

        saveBtn.setOnClickListener {
            for(i in userOptionList) {
                if(i.isSelected) {
                    selectedOption = i.title
                }
            }

            if(selectedOption == "") {
                Toast.makeText(context, "선택된 항목이 없습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                saveBtnClickListener.onClick(selectedOption)
                dismiss()
            }

        }
    }

    fun setMatchingChange(vpMatching : ViewPager2) {
        dialog!!.setContentView(R.layout.dialog_matching_change)

        var btnCancel = dialog!!.findViewById<Button>(R.id.btn_cancel)
        var btnChange = dialog!!.findViewById<Button>(R.id.btn_change)

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

        var textTitle = view.findViewById<TextView>(R.id.text_condition_title)
        var rvCondition = view.findViewById<RecyclerView>(R.id.rv_condition)
        var btnSave = view.findViewById<Button>(R.id.btn_save)

        textTitle.setText(title)
        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(view, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        rvCondition.setHasFixedSize(true)
        rvCondition.layoutManager=LinearLayoutManager(context!!,RecyclerView.VERTICAL,false)
        rvCondition.adapter=ConditionAdapter(context!!, conditionList)

        btnSave.setOnClickListener {
            if(title.contains("키") || title.contains("나이")){
                if(ConditionAdapter.selectedCondition.split(", ").size != 2){
                    Toast.makeText(context, "두 가지를 선택해주세요.",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else{
                    var array = ConditionAdapter.selectedCondition.split(", ")
                    array = array.sorted()
                    textView.setText("${array[0]} ~ ${array[1]}")
                    ConditionAdapter.selectedCondition = ""
                    dismiss()
                }
            }
            else {
                textView.setText(ConditionAdapter.selectedCondition)
                ConditionAdapter.selectedCondition = ""
                dismiss()
            }
        }
    }

    class BottomSheetDialog(var textView: TextView) : BottomSheetDialogFragment() {
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
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "취미"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_study)?.setOnClickListener {
                MakeRoomActivity.category="스터디"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "스터디"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_recruit)?.setOnClickListener {
                MakeRoomActivity.category="취/창업"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "취/창업"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_counsel)?.setOnClickListener {
                MakeRoomActivity.category="고민상담"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "고민상담"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_talk)?.setOnClickListener {
                MakeRoomActivity.category="잡담"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "잡담"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }
        }
    }
}