package com.uniting.android.Cafeteria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val activityType = intent.getStringExtra("activityType")
        val spaceDecroation = VerticalSpaceItemDecoration(20)

        if(activityType == "review") {
            var reviewList = intent.getSerializableExtra("reviewList") as ArrayList<CafeteriaItem.Review>

            rv_cafeteria_common.setHasFixedSize(true)
            rv_cafeteria_common.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rv_cafeteria_common.adapter = ReviewAdapter(this, reviewList, reviewList.size)
            rv_cafeteria_common.addItemDecoration(spaceDecroation)
        }
    }
}