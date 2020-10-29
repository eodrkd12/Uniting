package com.uniting.android.Option

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class InquireAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    var fa = fa
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> InquireFragment(fa)
            else -> {
                InquireListFragment()
            }
        }
    }


    override fun getItemCount(): Int {
        return 2
    }


}