package com.uniting.android.Home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R

class ConditionAdapter(val context: Context, val conditionList: ArrayList<String>) :
    RecyclerView.Adapter<ConditionAdapter.ViewHolder>() {

    companion object{
        var selectedCondition = ""
        var befTextView : TextView? = null
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionAdapter.ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_condition, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConditionAdapter.ViewHolder, position: Int) {
        var textCondition = holder.view.findViewById<TextView>(R.id.text_condition)
        textCondition.setText(conditionList[position])
        textCondition.setOnClickListener {
            if(befTextView!=null){
                befTextView!!.setTextColor(Color.parseColor("#9E9E9E"))
            }
            textCondition.setTextColor(context.getColor(R.color.colorPrimary))
            selectedCondition=textCondition.text.toString()
            befTextView=textCondition
        }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}