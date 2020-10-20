package com.uniting.android.Cafeteria

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.item_verticalcafeteria.view.*

class CafeteriaVerticalAdapter(val activity: Activity, val cafeteriaTypeList: ArrayList<ArrayList<CafeteriaItem.CafeteriaPreview>>) : RecyclerView.Adapter<CafeteriaVerticalAdapter.ViewHolder>() {
    val cafeteriaType = arrayListOf("한식", "중식", "양식", "일식", "치킨")

    override fun getItemCount(): Int {
        return cafeteriaTypeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaVerticalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_verticalcafeteria, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cafeteriaType.text = cafeteriaType.get(position)

        holder.horizontalCafeteriaRV.setHasFixedSize(true)
        holder.horizontalCafeteriaRV.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        holder.horizontalCafeteriaRV.adapter = CafeteriaHorizontalAdapter(activity, cafeteriaTypeList.get(position))

        Log.d("test", cafeteriaTypeList.get(position).toString())
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cafeteriaEtc = view.text_cafeteria_etc
        val horizontalCafeteriaRV = view.rv_horizontal_cafeteria
        val cafeteriaType = view.text_cafeteria_type
    }
}
