package com.lx.project5.schedule

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.lx.api.BasicClient
import com.lx.data.RouteTrackingResponse
import com.lx.data.SearchRouteTrackingResponse
import com.lx.project5.MainActivity
import com.lx.project5.R
import com.lx.project5.appdata.AppData
import com.lx.project5.databinding.FragmentDolbomIngBinding
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DolbomIngFragment : Fragment(),OnMapReadyCallback {
    var _binding: FragmentDolbomIngBinding? = null
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDolbomIngBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(com.lx.project5.R.id.dolbomMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding.dolbomIngToDBSchedule.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMschedule)
        }
        binding.matkimiInfoButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmatkimiInfo)
        }

        // 종료버튼은 화면 시작시 비활성화
        binding.finishWalkButton.visibility = View.GONE
        return binding.root
    }

    override fun onMapReady(googleMap:GoogleMap) {
        map = googleMap
        (activity as MainActivity).showToast("현재 위치를 확인하세요!")

        requestLocation()

        binding.startWalkButton.setOnClickListener {
            (activity as MainActivity).showToast("산책을 시작합니다")
            startRouteTracking()
            // 산책 중지버튼 활성화 & 시작버튼 삭제
            it.visibility = View.GONE
            binding.finishWalkButton.visibility = View.VISIBLE
        }

        binding.finishWalkButton.setOnClickListener {
            onStop()
            onRestart()
        }
    }

    private fun onRestart() {
        super.onResume()
        map.clear()
        requestLocation()
        (activity as MainActivity).showToast("산책이 종료되었습니다.")
        binding.finishWalkButton.visibility = View.GONE
        binding.startWalkButton.visibility = View.VISIBLE
    }


    fun startRouteTracking() {
        try {
            // 가장 최근에 확인된 위치 알려주기
            locationClient?.lastLocation?.addOnSuccessListener {
            }

            // 위치클라이언트 만들기
            locationClient =
                LocationServices.getFusedLocationProviderClient(activity as MainActivity)

            // 내위치를 요청할 때 필요한 설정값
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 10 * 1000
            }

            // 내위치를 받았을 때 자동으로 호출되는 함수
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for ((index, location) in result.locations.withIndex()) {

                        showCurrentLocation(location)
                        routeTrack?.set(0, LatLng(location.latitude, location.longitude))
                        routeTrack?.add(tempIndex, LatLng(location.latitude, location.longitude))

                        Log.v("showIndex", "${tempIndex}")
                        Log.v("showIndex", "${location.latitude}")
                        tempIndex++

                        drawRoute(routeTrack)
                    }
                }
            }
            // 내 위치 요청
            locationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun drawRoute(routeTrack: MutableList<LatLng>) {
        var exPoint = routeTrack[tempIndex-2]
        var currentPoint = routeTrack[tempIndex-1]

        map.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(exPoint,currentPoint)
                .endCap(RoundCap())
                .width(40F)
                .color(Color.RED)
                .jointType(JointType.ROUND)
        )
        insertRouteTracking(currentPoint)
    }

    fun insertRouteTracking(curPoint: LatLng) {
        Log.v("tag","${curPoint.latitude},${curPoint.longitude}")
        BasicClient.api.insertRouteTracking(
            requestCode = "1001",
            x = curPoint.latitude,
            y = curPoint.longitude,
            routeNo = AppData.routeNo
        ).enqueue(object : Callback<SearchRouteTrackingResponse> {
            override fun onResponse(call: Call<SearchRouteTrackingResponse>, response: Response<SearchRouteTrackingResponse>) {
                var tempRow = response.body()?.data?.get(0)?.routeNo
                Log.v("님","$tempRow")
            }
            override fun onFailure(call: Call<SearchRouteTrackingResponse>, t: Throwable) {
                Log.v("님","1111")
            }
        })
    }

    // 내 위치의 지도 보여주기
    fun showCurrentLocation(location: Location) {
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
    override fun onStop() {
        super.onStop()
    }
    fun requestLocation() {
        try {
            // 가장 최근에 확인된 위치 알려주기
            locationClient?.lastLocation?.addOnSuccessListener {
            }
            // 위치클라이언트 만들기
            locationClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)

            // 내위치를 요청할 때 필요한 설정값
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 5 * 1000
            }

            // 내위치를 받았을 때 자동으로 호출되는 함수
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for ((index, location) in result.locations.withIndex()) {
                        showCurrentLocation(location)
                    }
                }
            }
            // 내 위치 요청
            locationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}