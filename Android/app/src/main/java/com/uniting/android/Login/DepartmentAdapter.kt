package com.uniting.android.Login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_university.view.*

class DepartmentAdapter(val context: Context, val deptFilter:ArrayList<UniversityItem.Department>) : RecyclerView.Adapter<DepartmentAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onClick(name : String)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener : ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return deptFilter.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DepartmentAdapter.ViewHolder, position: Int) {
        holder.itemView.text_university.text = deptFilter.get(position).deptName
        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle("선택한 학과가 맞습니까?")
            builder.setMessage(deptFilter.get(position).deptName)

            builder.setNegativeButton("확인") { dialog, id->
                itemClickListener.onClick(deptFilter.get(position).deptName)
            }

            builder.setPositiveButton("취소") { dialog, id ->

            }
            builder.show()
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }

}