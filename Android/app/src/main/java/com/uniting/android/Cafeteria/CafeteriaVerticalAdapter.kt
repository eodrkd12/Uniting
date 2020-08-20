package com.uniting.android.Cafeteria

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Interface.NaverAPI
import com.uniting.android.R
import kotlinx.android.synthetic.main.item_verticalcafeteria.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CafeteriaVerticalAdapter(activity: Activity, val cafeteriaType: ArrayList<String>) : RecyclerView.Adapter<CafeteriaVerticalAdapter.ViewHolder>() {
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
            .baseUrl("https://store.naver.com/sogum/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NaverAPI::class.java)

        /*api.getCafeteriaList(1, 10, "성서계명대", "reviewCount").enqueue(object : Callback<ArrayList<CafeteriaItem.Cafeteria>> {
            override fun onResponse(call: Call<ArrayList<CafeteriaItem.Cafeteria>>, response: Response<ArrayList<CafeteriaItem.Cafeteria>>) {
                var array = response.body()

                for(i in array!!) {
                    Log.d("test", i.toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<CafeteriaItem.Cafeteria>>, t: Throwable) {
            }
        })*/
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cafeteriaEtc = view.text_cafeteria_etc
        val horizontalCafeteriaRV = view.rv_horizontal_cafeteria
        val cafeteriaType = view.text_cafeteria_type
    }
}