package com.uniting.android.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.R
import com.uniting.android.Room.MyRoomFragment

class HomeFragment : Fragment() {

    private var matchingFragment : Fragment? = null
    private var openChatFragment : Fragment? = null

    private var currentFragment = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_home, container, false)

        if (savedInstanceState == null) {
            matchingFragment = MatchingFragment()
            activity!!.supportFragmentManager.beginTransaction().add(R.id.frame_home,matchingFragment!!).commit()
        }

        var btnSwap = rootView.findViewById<Button>(R.id.btn_swap)
        btnSwap.setOnClickListener {
            when(currentFragment) {
                0 -> {
                    if(openChatFragment == null) {
                        openChatFragment = OpenChatFragment()
                        activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right).add(R.id.frame_home, openChatFragment!!).commit()
                    }

                    activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right).show(openChatFragment!!).commit()
                    activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right).hide(matchingFragment!!).commit()


                    /*activity!!.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right)
                        .replace(R.id.frame_home,openChatFragment!!).commit()*/
                    currentFragment=1
                    btnSwap.background=resources.getDrawable(R.drawable.by1_button)
                }
                1 -> {
                    if(matchingFragment == null) {
                        matchingFragment = MatchingFragment()
                        activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right).add(R.id.frame_home, matchingFragment!!).commit()
                    }

                    activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right).hide(openChatFragment!!).commit()
                    activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right).show(matchingFragment!!).commit()

                    currentFragment=0
                    btnSwap.background=resources.getDrawable(R.drawable.openchat_button)
                }
            }
        }

        return rootView
    }
}