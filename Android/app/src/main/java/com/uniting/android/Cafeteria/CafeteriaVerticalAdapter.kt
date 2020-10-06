package com.uniting.android.Cafeteria

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Interface.NaverAPI
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_verticalcafeteria.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CafeteriaVerticalAdapter(val activity: Activity, val cafeteriaType: ArrayList<String>) : RecyclerView.Adapter<CafeteriaVerticalAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return cafeteriaType.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaVerticalAdapter.ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_verticalcafeteria, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cafeteriaType.text = cafeteriaType.get(position)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NaverAPI::class.java)

        api.getCafeteriaList(1, 10, "성서계명대${cafeteriaType.get(position)}", "zjmsxbzZatZyy90LhgRy", "4F6vgTA6CE").enqueue(object : Callback<CafeteriaItem.CafeteriaList> {
            override fun onResponse(call: Call<CafeteriaItem.CafeteriaList>, response: Response<CafeteriaItem.CafeteriaList>) {
                if(response.isSuccessful) {
                    val success = response.body()

                    Log.d("test", cafeteriaType.get(position))

                    for(i in success!!.cafeteriaList)
                        Log.d("test", i.toString())

                    holder.horizontalCafeteriaRV.setHasFixedSize(true)
                    holder.horizontalCafeteriaRV.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                    holder.horizontalCafeteriaRV.adapter = CafeteriaHorizontalAdapter(activity, success!!.cafeteriaList)
                }

            }

            override fun onFailure(call: Call<CafeteriaItem.CafeteriaList>, t: Throwable) {
                Log.d("test", t.message!!)
            }
        })
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cafeteriaEtc = view.text_cafeteria_etc
        val horizontalCafeteriaRV = view.rv_horizontal_cafeteria
        val cafeteriaType = view.text_cafeteria_type
    }
}