package com.uniting.android.Cafeteria

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.naver.maps.geometry.Utmk
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.PSDialog
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cafeteria_inform.*
import org.jsoup.Jsoup


class CafeteriaInformActivity : PSAppCompatActivity(), OnMapReadyCallback {

    var mapx : Double = 0.0
    var mapy : Double = 0.0
    var id : String = ""
    var menuList = ArrayList<CafeteriaItem.Menu>()
    var imageList = ArrayList<String>()
    var reviewList = ArrayList<CafeteriaItem.Review>()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeteria_inform)

        var check = true

        val intent=intent

        mapx = intent.getIntExtra("x", 0).toDouble()
        mapy = intent.getIntExtra("y", 0).toDouble()
        //val name =intent.getStringExtra("name")
        //mapx = intent.getStringExtra("x")!!.toDouble()
        //mapy = intent.getStringExtra("y")!!.toDouble()
        /*var phone = intent.getStringExtra("phone")
        id = intent.getStringExtra("id").toString()
        val roadAddr = intent.getStringExtra("roadAddr")
        var options = intent.getStringExtra("options")
        var bizHourInfo = intent.getStringExtra("bizHourInfo")
        val tags = intent.getStringExtra("tags")


        //네이버 메뉴 파싱
        BackgroundTask()*/


        //지도 생성
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

        //리뷰 불러오기
        val spaceDecoration = VerticalSpaceItemDecoration(20) // RecyclerView 간격

        /*Retrofit.getReview(name!!) {
            reviewList.clear()
            if(it.size != 0) {
                reviewList = it
                text_no_review.visibility = View.GONE
                if(it.size < 3) {
                    rv_cafeteria_review.setHasFixedSize(true)
                    rv_cafeteria_review.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_cafeteria_review.adapter = ReviewAdapter(this, it, it.size)
                    rv_cafeteria_review.addItemDecoration(spaceDecoration)
                } else {
                    rv_cafeteria_review.setHasFixedSize(true)
                    rv_cafeteria_review.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_cafeteria_review.adapter = ReviewAdapter(this, it, 3)
                    rv_cafeteria_review.addItemDecoration(spaceDecoration)
                }

                text_review_etc.setOnClickListener {
                    var intent = Intent(this, RecyclerViewActivity::class.java)
                    intent.putExtra("reviewList", reviewList)
                    intent.putExtra("activityType", "review")
                    startActivity(intent)
                }

            } else {
                text_no_review.visibility = View.VISIBLE
            }
        }*/

        /*//리뷰 더보기



        if(bizHourInfo == "" || bizHourInfo == null)
        {
            text_bizhourinfo.setTextColor(R.color.colorMdGrey_900)
            bizHourInfo = "정보없음"
        }

        if(options == "" || options == null)
        {
            options = "정보없음"
        }

        if(phone == "" || phone == null)
        {
            phone = "정보없음"
            text_phone.setTextColor(R.color.colorMdGrey_900)
        } else {
            text_phone.setOnClickListener {
                val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
                builder.setTitle(name)
                builder.setMessage(phone)

                builder.setNegativeButton("통화") { dialog, id->
                    Log.d("test", phone)
                    val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}"))
                    try {
                        startActivity(call)
                    } catch (e: Exception) {
                        Log.d("test",e.toString())
                        e.printStackTrace()
                    }
                }
                builder.setPositiveButton("취소") { dialog, id ->

                }
                builder.show()
            }
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
                var intent = Intent(this, WriteReviewActivity::class.java)
                intent.putExtra("rating", rating_inform.rating)
                intent.putExtra("cafeteriaName", name)
                startActivity(intent)
                rating_inform.rating = 0.0f
            }
        }*/

    }

    override fun onMapReady(naverMap: NaverMap) {
        val test = Tm128(mapx, mapy)

        //val cameraUpdate = CameraUpdate.scrollTo(LatLng(mapy!!,mapx!!))
        val cameraUpdate = CameraUpdate.scrollTo(test.toLatLng())
        val zoomUpdate = CameraUpdate.zoomTo(18.0)
        naverMap.moveCamera(cameraUpdate)
        naverMap.moveCamera(zoomUpdate)

        val marker = Marker()
        //marker.position = LatLng(mapy!!, mapx!!)
        marker.position = test.toLatLng()
        marker.map = naverMap
    }

    var backgroundtask: Disposable? = null

    fun BackgroundTask() {
        backgroundtask =
            Observable.fromCallable<Any> {
                // doInBackground
                val url = "https://store.naver.com/restaurants/detail?id=$id"
                val imageUrl = "https://store.naver.com/restaurants/detail?id=$id&tab=photo"
                try {
                    val doc = Jsoup.connect(url).get()
                    val imageDoc = Jsoup.connect(imageUrl).get()
                    val menuData = doc.select("ul[class=list_menu]").select("li")
                    val imageData = imageDoc.select("div[class=flick_content]").select("div[class=thumb]")

                    imageList.clear()
                    imageData.forEachIndexed { index, element ->
                        val imageUrl = element.select("img").attr("src")
                        Log.d("test", imageUrl)
                        imageList.add(imageUrl)
                    }
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
                    if(menuList.size == 0) {
                        text_no_menu.visibility = View.VISIBLE
                    } else {
                        text_no_menu.visibility = View.GONE
                        rv_cafeteria_menu.adapter = MenuAdapter(menuList)
                        rv_cafeteria_menu.setHasFixedSize(true)
                        rv_cafeteria_menu.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    }

                    val gridSpaceDecoration = GrideSpaceItemDecoration(1)

                    if(imageList.size < 6) {
                        rv_cafeteria_image.setHasFixedSize(true)
                        rv_cafeteria_image.adapter = CafeteriaImageAdapter(this, imageList, imageList.size)
                        rv_cafeteria_image.layoutManager = GridLayoutManager(this, 3)
                        rv_cafeteria_image.addItemDecoration(gridSpaceDecoration)
                    }
                    else {
                        rv_cafeteria_image.setHasFixedSize(true)
                        rv_cafeteria_image.adapter = CafeteriaImageAdapter(this, imageList, 6)
                        rv_cafeteria_image.layoutManager = GridLayoutManager(this, 3)
                        rv_cafeteria_image.addItemDecoration(gridSpaceDecoration)
                    }
                    backgroundtask?.dispose()
                }
    }
}