package com.uniting.android.Class

import android.graphics.Rect
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Interface.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

abstract class PSAppCompatActivity : AppCompatActivity(){

    fun getCurDate() : String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())

        return curDate
    }
    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }

    inner class GrideSpaceItemDecoration(private val spaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = spaceHeight
            outRect.right = spaceHeight
            outRect.left = spaceHeight
        }
    }

}