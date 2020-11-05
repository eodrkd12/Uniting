package com.uniting.android.Cafeteria
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_cafeteria_inform.*


class CafeteriaInformActivity : PSAppCompatActivity(), OnMapReadyCallback {

    lateinit var reviewList : ArrayList<CafeteriaItem.Review>
    lateinit var menuList : ArrayList<CafeteriaItem.Menu>
    var mapx : Double = 0.0
    var mapy : Double = 0.0
    var cafeName : String = ""
    var cafePhone : String = ""
    var check = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeteria_inform)

        val cafeNo = intent.getIntExtra("cafe_no", 0)
        Log.d("test", "$cafeNo")
        val spaceDecoration = VerticalSpaceItemDecoration(50) // RecyclerView 간격

        //식당정보 불러오기
        Retrofit.getCafeteriaInform(cafeNo) {
            text_inform_title.text = it.inform.cafeName
            text_inform_road_addr.text = it.inform.cafeAddress
            text_inform_phone.text = it.inform.cafePhone

            cafeName = it.inform.cafeName
            cafePhone = it.inform.cafePhone
            mapx = it.inform.mapx
            mapy = it.inform.mapy

            reviewList = it.reviewList
            menuList = it.menuList

            //지도 생성
            val fm = supportFragmentManager
            val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.map, it).commit()
                }
            mapFragment.getMapAsync(this)


            //전화번호 클릭이벤트
            if(it.inform.cafePhone != "정보없음") {
                text_inform_phone.setTextColor(Color.parseColor("#42A5F5"))
                text_inform_phone.setOnClickListener {
                    val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
                    builder.setTitle(cafeName)
                    builder.setMessage(cafePhone)

                    builder.setNegativeButton("통화") { dialog, id->
                        val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${cafePhone}"))
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

            if("|" in it.inform.cafeBizHour)
            {
                text_inform_bizhour.setTextColor(Color.parseColor("#42A5F5"))
                val index:Int =  it.inform.cafeBizHour.indexOf("|")
                var timeOpenClose:String = it.inform.cafeBizHour.substring(0, index)
                text_inform_bizhour.text = "$timeOpenClose ∨"
                var timeData:String = it.inform.cafeBizHour.substring(index+1, it.inform.cafeBizHour.length).replace("|", "\n")

                text_inform_bizhour.setOnClickListener{
                    if(check)
                    {
                        text_inform_bizhour.text = "$timeOpenClose ∧\n$timeData"
                        check = false
                    }
                    else
                    {
                        text_inform_bizhour.text = "$timeOpenClose ∨"
                        check = true
                    }
                }
            } else {
                text_inform_bizhour.text = it.inform.cafeBizHour
            }

            if(reviewList.size == 0) {
                text_no_review.visibility = View.VISIBLE
            } else {
                if(reviewList.size < 3) {
                    rv_cafeteria_review.setHasFixedSize(true)
                    rv_cafeteria_review.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_cafeteria_review.adapter = ReviewAdapter(this, reviewList, reviewList.size)
                    rv_cafeteria_review.addItemDecoration(spaceDecoration)
                } else {
                    rv_cafeteria_review.setHasFixedSize(true)
                    rv_cafeteria_review.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_cafeteria_review.adapter = ReviewAdapter(this, reviewList, 3)
                    rv_cafeteria_review.addItemDecoration(spaceDecoration)
                }

                text_review_etc.setOnClickListener {
                    val intent = Intent(this, RecyclerViewActivity::class.java)
                    intent.putExtra("reviewList", reviewList)
                    intent.putExtra("activityType", "review")
                    startActivity(intent)
                }
            }

            if(menuList.size == 0) {
                text_no_menu.visibility = View.VISIBLE
            } else {
                if(menuList.size < 5) {
                    rv_cafeteria_menu.setHasFixedSize(true)
                    rv_cafeteria_menu.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_cafeteria_menu.adapter = MenuAdapter(menuList, menuList.size)
                } else {
                    rv_cafeteria_menu.setHasFixedSize(true)
                    rv_cafeteria_menu.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_cafeteria_menu.adapter = MenuAdapter(menuList, 5)
                }

                text_menu_etc.setOnClickListener {
                    val intent = Intent(this, RecyclerViewActivity::class.java)
                    intent.putExtra("menuList", menuList)
                    intent.putExtra("activityType", "menu")
                    startActivity(intent)
                }
            }
        }

        rating_inform.setOnRatingBarChangeListener{ ratingBar, fl, b ->
            if(rating_inform.rating != 0.0f) {
                var intent = Intent(this, WriteReviewActivity::class.java)
                intent.putExtra("rating", rating_inform.rating)
                intent.putExtra("cafe_no", cafeNo)
                startActivity(intent)
                rating_inform.rating = 0.0f
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

    // Asynctask 대체
    /*fun BackgroundTask() {
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
    }*/
}