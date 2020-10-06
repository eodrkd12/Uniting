package com.uniting.android.Option

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.uniting.android.Class.UserInfo
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.fragment_inquire_list.*
import org.json.JSONObject

class InquireListFragment : Fragment() {

    var inquireList = ArrayList<InquireItem.Inquire>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_inquire_list, container, false)

        val inquireRV : RecyclerView = rootView.findViewById(R.id.rv_inquire)
        val emptyInquireText : TextView = rootView.findViewById(R.id.text_emptyinquire)
        val swipeLayout: SwipeRefreshLayout = rootView.findViewById(R.id.swipe_inquire)

        swipeLayout.setOnRefreshListener {
            inquireList.clear()
            Retrofit.getInquireList(UserInfo.ID) {
                inquireList = it
                if(inquireList.size == 0) {
                    emptyInquireText.visibility = View.VISIBLE
                }
                else {
                    emptyInquireText.visibility = View.GONE
                    inquireRV.setHasFixedSize(true)
                    inquireRV.layoutManager =
                        LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
                    inquireRV.adapter = InquireListAdapter(activity!!, inquireList)
                }
                swipe_inquire.setRefreshing(false)
            }
        }

        Retrofit.getInquireList(UserInfo.ID) {
            Log.d("test", it.toString())
            inquireList = it
            if(inquireList.size == 0) {
                emptyInquireText.visibility = View.VISIBLE
            }
            else {
                emptyInquireText.visibility = View.GONE
                inquireRV.setHasFixedSize(true)
                inquireRV.layoutManager =
                    LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
                inquireRV.adapter = InquireListAdapter(activity!!, inquireList)
            }
            swipe_inquire.setRefreshing(false)
        }

        return rootView
    }
}