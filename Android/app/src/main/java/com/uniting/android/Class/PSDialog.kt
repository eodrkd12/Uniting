package com.uniting.android.Class

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

    fun setWriteReview(rating: Float) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_write_review, null)
        val addPhotoBtn : TextView = dialogView.findViewById(R.id.text_add_photo)
        val ratingBar : RatingBar = dialogView.findViewById(R.id.rating_insert_review)
        val reviewImage : ImageView = dialogView.findViewById(R.id.image_write_review)
        val reviewContentText : EditText = dialogView.findViewById(R.id.edit_review_content)
        val writeBtn : TextView = dialogView.findViewById(R.id.text_write_review)

        ratingBar.setRating(rating)
        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.AnimationPopupStyle
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))



    }

}