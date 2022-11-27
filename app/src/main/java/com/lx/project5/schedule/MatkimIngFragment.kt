package com.lx.project5.schedule

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.lx.api.BasicClient
import com.lx.data.SearchRouteTrackingResponse
import com.lx.project5.MainActivity
import com.lx.project5.appdata.AppData
import com.lx.project5.appdata.AppData.Companion.routeNo
import com.lx.project5.databinding.FragmentMatkimIngBinding
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MatkimIngFragment : Fragment(), OnMapReadyCallback {
    var _binding: FragmentMatkimIngBinding? = null
    val binding get() = _binding!!

    //지도
    lateinit var map: GoogleMap
    var locationClient: FusedLocationProviderClient? = null
    var myMarker: Marker? = null
    // 산책 경로
    var tempIndex = 1
    var latitude = 0.0
    var longitude:Double = 0.0
    var latLng = LatLng(latitude,longitude)
    var routeTrack = mutableListOf(latLng)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMatkimIngBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(com.lx.project5.R.id.matkimMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // 배열 초기화
        routeTrack = mutableListOf(latLng)
        nowTime()

        binding.matkimingToMKSchedule.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMschedule)
        }
        binding.petSitterInfoButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolbomiInfo)
        }
        return binding.root
    }

    override fun onMapReady(googleMap:GoogleMap) {
        map = googleMap

        startRouteTracking()
    }

    private fun startRouteTracking() {
        BasicClient.api.searchRouteTracking(
            requestCode = "1001",
            routeNo = routeNo
        ).enqueue(object : Callback<SearchRouteTrackingResponse> {
            override fun onResponse(call: Call<SearchRouteTrackingResponse>, response: Response<SearchRouteTrackingResponse>) {
                Log.v("lastkingdom","근처 마커 활성화 요청 성공")
                val jsonArray = JSONArray(response.body()?.data)
                var lastX = 0.0
                var lastY = 0.0
                for (i in 0 until jsonArray.length()) {
                    Log.v("lastkingdom","근처 마커 for문 진입")
                    response.body()?.data?.get(i)?.point?.apply {
                        if (this.x == 0.0 || this.y == 0.0) {

                        } else {
                            routeTrack.add(i, LatLng(this.x, this.y))

                            lastX = this.x
                            lastY = this.y
                        }
                    }
                }
                showCurrentLocation(LatLng(lastX, lastY))

                routeTrack.remove(LatLng(0.0, 0.0))
                Log.i("Mat", "routeTrack : ${routeTrack.joinToString(",")}")

                drawRoute(routeTrack)
                Log.v("tlqkf","$routeTrack")
            }
            override fun onFailure(call: Call<SearchRouteTrackingResponse>, t: Throwable) {
                Log.v("lastkingdom","근처 마커 활성화 요청 실패")
            }
        })
    }

    private fun drawRoute(routeTrack: MutableList<LatLng>) {
        map.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(routeTrack)
                .endCap(RoundCap())
                .width(40F)
                .color(Color.BLACK)
                .jointType(JointType.ROUND)
        )
    }
    // 내 위치의 지도 보여주기
    fun showCurrentLocation(location: LatLng) {
        val curPoint = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17.0f))
        Log.v("lastkingdom","showCurrentLocation")
        showMarker(curPoint)
    }

    fun showMarker(curPoint: LatLng) {
        myMarker?.remove()
        MarkerOptions().also {
            it.position(curPoint)
            it.title("내위치")
            it.icon(BitmapDescriptorFactory.fromResource(com.lx.project5.R.drawable.dogicon))

            myMarker = map.addMarker(it)
            myMarker?.tag = "1001"
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun nowTime(){
        val nownow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH"))
        val now1 = nownow.toInt() - 2
        val now2 = nownow.toInt() + 2
        binding.matkimTime1.text = "${now1} 시 ~ ${now2} 시"
    }

}