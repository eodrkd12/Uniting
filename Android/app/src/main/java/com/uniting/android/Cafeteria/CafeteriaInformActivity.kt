package com.uniting.android.Cafeteria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.uniting.android.R
import kotlinx.android.synthetic.main.activity_cafeteria_inform.*

class CafeteriaInformActivity : AppCompatActivity(), OnMapReadyCallback {
    var mapx : Double = 0.0
    var mapy : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeteria_inform)

        var check = true

        val intent=intent
        val name =intent.getStringExtra("name")
        mapx = intent.getStringExtra("x")!!.toDouble()
        mapy = intent.getStringExtra("y")!!.toDouble()
        var phone = intent.getStringExtra("phone")
        val id = intent.getStringExtra("id")
        val roadAddr = intent.getStringExtra("roadAddr")
        var options = intent.getStringExtra("options")
        var bizHourInfo = intent.getStringExtra("bizHourInfo")
        val tags = intent.getStringExtra("tags")

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
}