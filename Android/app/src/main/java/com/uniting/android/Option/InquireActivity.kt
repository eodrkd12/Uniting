package com.uniting.android.Option

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_inquire.*

class InquireActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquire)

        val vpAdapter = InquireAdapter(this)

        val tabLayoutTextArray = arrayOf("문의하기","문의내역")

        viewpager_inquire.adapter = vpAdapter

        viewpager_inquire.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(position == 1) {
                }
            }
        })

        TabLayoutMediator(tabLayout_inquire, viewpager_inquire) {tab, position ->
            tab.text = tabLayoutTextArray[position]
            viewpager_inquire.setCurrentItem(tab.position, true)
        }.attach()
    }
}