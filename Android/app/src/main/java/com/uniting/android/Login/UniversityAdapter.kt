package com.uniting.android.Login

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_university.view.*

class UniversityAdapter(val context: Context, val univFilter:ArrayList<UniversityItem.University>) : RecyclerView.Adapter<UniversityAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onClick(name: String, mail: String)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener : ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun getItemCount(): Int {
        return univFilter.count()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UniversityAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UniversityAdapter.ViewHolder, position: Int) {
        holder.univName.text = univFilter.get(position).univName
        holder.view.setOnClickListener {
            val builder = AlertDialog.Builder(
                ContextThemeWrapper(
                    context,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )
            builder.setTitle("선택한 학교가 맞습니까?")
            builder.setMessage(univFilter.get(position).univName)

            builder.setNegativeButton("확인") { dialog, id ->
                itemClickListener.onClick(univFilter.get(position).univName, univFilter.get(position).univMail)
            }

            builder.setPositiveButton("취소") { dialog, id ->

            }

            builder.show()
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val univName : TextView = view.findViewById(R.id.text_university)
    }
}