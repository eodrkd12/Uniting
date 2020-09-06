package com.uniting.android.Class

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uniting.android.R
import java.text.SimpleDateFormat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uniting.android.Room.MakeRoomActivity
import kotlinx.android.synthetic.main.dialog_category.*

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
            view?.findViewById<TextView>(R.id.text_hobby)?.setOnClickListener {
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
                MakeRoomActivity.category="아무말"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "아무말"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }
        }
    }
}