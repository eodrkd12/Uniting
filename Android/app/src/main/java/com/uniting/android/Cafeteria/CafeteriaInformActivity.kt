package com.uniting.android.Cafeteria

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.uniting.android.Class.PSDialog
import com.uniting.android.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cafeteria_inform.*
import org.jsoup.Jsoup


class CafeteriaInformActivity : AppCompatActivity(), OnMapReadyCallback {

    var mapx : Double = 0.0
    var mapy : Double = 0.0
    var id : String = ""
    var menuList = ArrayList<CafeteriaItem.Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeteria_inform)

        var check = true

        val intent=intent
        val name =intent.getStringExtra("name")
        mapx = intent.getStringExtra("x")!!.toDouble()
        mapy = intent.getStringExtra("y")!!.toDouble()
        var phone = intent.getStringExtra("phone")
        id = intent.getStringExtra("id").toString()
        val roadAddr = intent.getStringExtra("roadAddr")
        var options = intent.getStringExtra("options")
        var bizHourInfo = intent.getStringExtra("bizHourInfo")
        val tags = intent.getStringExtra("tags")


        BackgroundTask()

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        if(bizHourInfo == "" || bizHourInfo == null)
        {
            bizHourInfo = "정보없음"
        }

        if(options == "" || options == null)
        {
            options = "정보없음"
        }

        if(phone == "" || phone == null)
        {
            phone = "정보없음"
        }

        if("|" in bizHourInfo!!)
        {
            var index:Int =  bizHourInfo!!.indexOf("|")
            var timeOpenClose:String = bizHourInfo!!.substring(0, index-1)
            text_bizhourinfo.text = timeOpenClose + " ∨"
            var timeData:String = bizHourInfo!!.substring(index+2, bizHourInfo!!.length).replace(" | ", "\n")
            text_bizhourinfo.setOnClickListener{
                if(check == true)
                {
                    text_bizhourinfo.setText(timeOpenClose + " ∧\n" + timeData)

                    check = false
                }
                else
                {
                    text_bizhourinfo.text = timeOpenClose + " ∨"
                    check = true
                }
            }
        }
        else
        {
            text_bizhourinfo.text = bizHourInfo
        }


        text_inform_title.text = name
        text_roadaddr.text = roadAddr
        text_phone.text = phone


        rating_inform.setOnRatingBarChangeListener{ ratingBar, fl, b ->
            if(rating_inform.rating != 0.0f) {
                val psDialog = PSDialog(this)
                psDialog.setWriteReview(ratingBar.rating)
                rating_inform.rating = 0.0f
                psDialog.show()
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(mapy!!,mapx!!))
        val zoomUpdate = CameraUpdate.zoomTo(18.0)
        naverMap.moveCamera(cameraUpdate)
        naverMap.moveCamera(zoomUpdate)

        val marker = Marker()
        marker.position = LatLng(mapy!!, mapx!!)
        marker.map = naverMap
    }

    var backgroundtask: Disposable? = null

    fun BackgroundTask() {
        backgroundtask =
            Observable.fromCallable<Any> {

                // doInBackground
                val url = "https://store.naver.com/restaurants/detail?id=$id"
                try {
                    val doc = Jsoup.connect(url).get()
                    val menuData = doc.select("ul[class=list_menu]").select("li")
                    //timeData = doc.select("div.biztime > span").text()
                    menuList.clear()
                    menuData.forEachIndexed { index, element ->
                        val menuName = element.select("li span[class=name]").text()
                        val menuPrice = element.select("li em[class=price]").text()
                        menuList?.add(CafeteriaItem.Menu(menuName, menuPrice))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                false
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result: Any? ->
                    // onPostExecute
                    rv_cafeteria_menu.adapter = MenuAdapter(menuList)
                    rv_cafeteria_menu.setHasFixedSize(true)
                    rv_cafeteria_menu.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    backgroundtask?.dispose()
                }
    }
}