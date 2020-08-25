package com.uniting.android.Class

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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


}