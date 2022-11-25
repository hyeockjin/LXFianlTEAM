package com.lx.project5

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.lx.api.BasicClient
import com.lx.data.FileUploadResponse
import com.lx.data.careMarkerResponse
import com.lx.data.mkMarkerResponse
import com.lx.data.simpleMarkerResponse
import com.lx.project5.appdata.AppData
import com.lx.project5.appdata.CardData
import com.lx.project5.chatting.ChatListFragment
import com.lx.project5.databinding.ActivityMainBinding
import com.lx.project5.mypage.*
import com.lx.project5.schedule.*
import com.permissionx.guolindev.PermissionX
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityMainBinding
    var locationClient: FusedLocationProviderClient? = null
    lateinit var map: GoogleMap
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor // 간단한 데이터 저장용 생명주기 관리 할 필요없이 데이터가 저장된다

    var myMarker: Marker? = null
    var crMarker: Marker? = null
    var mkMarker: Marker? = null
    var simpleMarker :Marker? = null

    enum class ScreenItem {
        ITEM1,
        ITEMchat,
        ITEMjoin1,
        ITEMjoin2,
        ITEMlogin,
        ITEMmatkimguel,
        ITEMsincheonggeul,

        // 하단 내비 두번째 버튼눌렀을때 돌봄맡김 스케쥴 버튼
        ITEMdbschedule,
        ITEMmkschedule,
        ITEMschedule,

        // 돌봄 맡김 스케쥴에서 돌봄 맡김 중 화면
        ITEMdolboming,
        ITEMmatkiming,
        ITEMdolbomiInfo,
        ITEMmatkimiInfo,

        // 마이페이지
        ITEMdbhistory,
        ITEMdbrequestlist,
        ITEMhistory,
        ITEMmkhistory,
        ITEMmkrequestlist,
        ITEMmypage,
        ITEMrequestlist,
        ITEMupdate,
        ITEMchatlist,
        ITEMmypageMKInfo,
        ITEMmypageDBInfo,
        ITEMadddog,
        ITEMdoglist,
        ITEMdoglist1

    }

    val dateFormat1 = SimpleDateFormat("yyyyMMddHHmmss")
    var filename: String? = null

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        // 맡김 돌봄 구분자 미리 넣어 놓기 / 1일때 맡김이모드
        AppData.navIndex = 1


        if (currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }
    //화면시작시
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            onFragmentChanged(ScreenItem.ITEMchat)
        }
        binding.cardView.setOnClickListener {
            onFragmentChanged(ScreenItem.ITEM1)
        }
        // 주변에 돌봄요청 버튼 눌렀을 때
        binding.mainWriteButton.setOnClickListener {
            onFragmentChanged(ScreenItem.ITEMmatkimguel)
        }
        //하단 탭의 버튼을 눌렀을때
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab1 -> {
                    onFragmentChanged(ScreenItem.ITEM1)
                }
                R.id.tab2 -> {
                    // 로그인 상태에 따라 스케줄을 보여줄 것인지, 로그인 페이지로 이동할 것인지 선택 (default 로그인)
                    if(AppData.memberData?.memberId == null ){
                        showToast("로그인 먼저 해주세요!")
                    }else if(AppData.memberData?.memberId != null) {
                        onFragmentChanged(ScreenItem.ITEMschedule)
                    }
                }
                R.id.tab3 -> {
                    // 로그인 상태에 따라 마이페이지를 보여줄 것인지, 로그인 페이지로 이동할 것인지 선택 (default 로그인)
                    if(AppData.memberData?.memberId == null ){
                        showToast("로그인 먼저 해주세요!")
                    }else if(AppData.memberData?.memberId != null) {
                        onFragmentChanged(ScreenItem.ITEMchat)
                    }
                }
                R.id.tab4 -> {
                    // 로그인 상태에 따라 마이페이지를 보여줄 것인지, 로그인 페이지로 이동할 것인지 선택 (default 로그인)
                    if (AppData.memberData?.memberId == null) {
                        onFragmentChanged(ScreenItem.ITEMlogin)
                    } else if (AppData.memberData?.memberId != null) {
                        onFragmentChanged(ScreenItem.ITEMmypage)
                    }
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        binding.cardView.visibility = View.GONE
        // 위험권한 요청하기
        PermissionX.init(this)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            ).request { allGranted, grantedList, deniedList ->if (allGranted) {showToast("모든 권한 부여됨.")} else {showToast("거부된 권한 있음.")}}

        // 지도 초기화하기
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it

            // 내 위치 요청하기
            requestLocation()

            // 지도 클릭시 카드 뷰 없애기
            map.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
                override fun onMapClick(latLng: LatLng) {
                    Log.v("시발","onMapClick")
                    binding.cardView.visibility = View.GONE
                }
            })

            // 보고있는 지도 영역 구분
            map.setOnCameraIdleListener {
                val bounds = map.projection.visibleRegion.latLngBounds
                //showToast("좌상단 : ${bounds.northeast}, ${bounds.southwest}")

                val zoomLevel = map.cameraPosition.zoom
                println("zoomLevel : ${zoomLevel}")
            }

            showNearCRLocationMarker(map)
            binding.mainDBButton.setOnClickListener{
                showNearCRLocationMarker(map)
                binding.mainMKButton.setVisibility(View.VISIBLE);
                binding.mainDBButton.setVisibility(View.INVISIBLE);
            }
            binding.mainMKButton.setOnClickListener{
                showNearMKLocationMarker(map)
                binding.mainMKButton.setVisibility(View.INVISIBLE);
                binding.mainDBButton.setVisibility(View.VISIBLE);
            }
            //상단 애견삽,맛집, 산책경로 버튼
            binding.buttonHospital.setOnClickListener {
                showHospitalMarker()
            }
            binding.buttonDining.setOnClickListener {
                showDiningMarker(map)
            }
            binding.buttonHair.setOnClickListener {
                showHairMarker()
            }
            // 마커클릭
            map.setOnMarkerClickListener { it ->
                binding.cardView1.visibility = View.VISIBLE
                Log.v("시발", "setOnMarkerClickListener")

                var cardData = CardData()
                cardData.cardEmail = it.title
                cardData.doCard(cardData)
                binding.cardName.text = cardData.cardName
                binding.classScope.text = cardData.cardScope
                binding.cardAddress.text = cardData.cardAddress
                binding.cardDetail.text = cardData.cardDetail
                binding.cardTag.text = cardData.cardTag
                cardData.cardImage?.apply {
                    binding.cardImage.setImageResource(cardData.cardImage!!)
                }
                true
            }
        }
    } //@@@@@

    //심플 마커 전부 줄여주세요
    private fun showHospitalMarker() {
        map.clear()
        requestLocation()
        Log.v("lastkingdom", "showHospitalMarker")
        BasicClient.api.hospitalMarker(
            requestCode = "1001"
        ).enqueue(object : Callback<simpleMarkerResponse> {
            override fun onResponse(
                call: Call<simpleMarkerResponse>,
                response: Response<simpleMarkerResponse>
            ) {
                val jsonArray = JSONArray(response.body()?.data)
                for (i in 0 until jsonArray.length()) {
                    response.body()?.data?.get(i)?.apply {
                        var latitude = this.x
                        var longitude = this.y

                        // 1. 마커 옵션 설정 (만드는 과정)
                        var makerOptions = MarkerOptions()
                        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                            .position(LatLng(latitude, longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital))

                        Log.v("시발", " 마커 생성 ${makerOptions.title}")

                        // 2. 마커 생성 (마커를 나타냄)
                        simpleMarker = map.addMarker(makerOptions)
                        Log.v("시발", " 마커 생성 ${simpleMarker}")
                    }
                }
            }
            override fun onFailure(call: Call<simpleMarkerResponse>, t: Throwable) {
                Log.v("lastkingdom", "근처 마커 활성화 요청 실패")
            }
        })
    }
    private fun showHairMarker() {
        map.clear()
        requestLocation()
        Log.v("lastkingdom", "showHairMarker")
        BasicClient.api.hairMarker(
            requestCode = "1001"
        ).enqueue(object : Callback<simpleMarkerResponse> {
            override fun onResponse(
                call: Call<simpleMarkerResponse>,
                response: Response<simpleMarkerResponse>
            ) {
                val jsonArray = JSONArray(response.body()?.data)
                for (i in 0 until jsonArray.length()) {
                    response.body()?.data?.get(i)?.apply {
                        var latitude = this.x
                        var longitude = this.y
                        // 1. 마커 옵션 설정 (만드는 과정)
                        var makerOptions = MarkerOptions()
                        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                            .position(LatLng(latitude, longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hair))

                        Log.v("team", "${makerOptions.title}")

                        // 2. 마커 생성 (마커를 나타냄)
                        simpleMarker = map.addMarker(makerOptions)
                    }
                }
            }
            override fun onFailure(call: Call<simpleMarkerResponse>, t: Throwable) {
                Log.v("lastkingdom", "근처 마커 활성화 요청 실패")
            }
        })
    }
    private fun showDiningMarker(map: GoogleMap) {
        map.clear()
        requestLocation()
        Log.v("lastkingdom", "showDiningMarker")
        BasicClient.api.diningMarker(
            requestCode = "1001"
        ).enqueue(object : Callback<simpleMarkerResponse> {
            override fun onResponse(call: Call<simpleMarkerResponse>, response: Response<simpleMarkerResponse>) {
                val jsonArray = JSONArray(response.body()?.data)
                for (i in 0 until jsonArray.length()) {
                        var latitude = response.body()?.data?.get(i)?.x
                        var longitude = response.body()?.data?.get(i)?.y
                        // 1. 마커 옵션 설정 (만드는 과정)
                        var makerOptions = MarkerOptions()

                        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                            .position(LatLng(latitude!!, longitude!!)).title("상점위치").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_dining))
                        Log.v("tlqkf","$latitude,$longitude")
                        // 2. 마커 생성 (마커를 나타냄)
                        simpleMarker = map.addMarker(makerOptions)
                }
            }
            override fun onFailure(call: Call<simpleMarkerResponse>, t: Throwable) {
                Log.v("lastkingdom", "근처 마커 활성화 요청 실패")
            }
        })
    }

    // 근처 돌보미, 맡김이 마커 표시
    fun showNearCRLocationMarker(map: GoogleMap) { // 기존 마커 제거하고 생성
        map.clear()
        requestLocation()
        Log.v("lastkingdom", "showNearCRLocationMarker")
        BasicClient.api.careMarker(
            requestCode = "1001"
        ).enqueue(object : Callback<careMarkerResponse> { override fun onResponse(call: Call<careMarkerResponse>, response: Response<careMarkerResponse>) {
                val jsonArray = JSONArray(response.body()?.data)
                for (i in 0 until jsonArray.length()) {
                    var latitude = response.body()?.data?.get(i)?.careX
                    var longitude = response.body()?.data?.get(i)?.careY

                    // 1. 마커 옵션 설정 (만드는 과정)
                    var makerOptions = MarkerOptions()
                    makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(LatLng(latitude!!, longitude!!))
                        .title(response.body()?.data?.get(i)?.careId.toString())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_dog))

                    Log.v("시발","${makerOptions.title}")

                    // 2. 마커 생성 (마커를 나타냄)
                    crMarker = map.addMarker(makerOptions)
                }
            }
            override fun onFailure(call: Call<careMarkerResponse>, t: Throwable) {
                Log.v("lastkingdom", "근처 마커 활성화 요청 실패")
            }
        })
    }
    fun showNearMKLocationMarker(map: GoogleMap) {
        map.clear()
        requestLocation()
        BasicClient.api.mkMarker(
        requestCode = "1001"
        ).enqueue(object : Callback<mkMarkerResponse> {
            override fun onResponse(
                call: Call<mkMarkerResponse>,
                response: Response<mkMarkerResponse>
            ) {
                val jsonArray = JSONArray(response.body()?.data)
                for (i in 0 until jsonArray.length()) {
                    var latitude = response.body()?.data?.get(i)?.mkX
                    var longitude = response.body()?.data?.get(i)?.mkY
                    Log.v("lastkingdom", "showNearMKLocationMarker")

                    // 1. 마커 옵션 설정 (만드는 과정)
                    var makerOptions = MarkerOptions()
                    makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(LatLng(latitude!!, longitude!!))
                        .title(response.body()?.data?.get(i)?.mkId.toString()) // 타이틀.
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_person))

                    var mkId = response.body()?.data?.get(i)?.mkId.toString()
                    Log.v("tlqkf","${mkId}")

                    // 2. 마커 생성 (마커를 나타냄)
                    mkMarker = map.addMarker(makerOptions)
                }
            }
            override fun onFailure(call: Call<mkMarkerResponse>, t: Throwable) {
                Log.v("lastkingdom", "근처 마커 활성화 요청 실패")
            }
        })
    }

    //화면전환
    fun onFragmentChanged(index: MainActivity.ScreenItem) {
        when (index) {
            MainActivity.ScreenItem.ITEM1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, FirstFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMchat -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ChatListFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMjoin1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Join1Fragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMjoin2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Join2Fragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMlogin -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMdbschedule -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DBScheduleFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMschedule -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ScheduleFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMmkschedule -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MKScheduleFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMdolboming -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DolbomIngFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMmatkiming -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MatkimIngFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMdolbomiInfo -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DolbomiInfoFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMmatkimiInfo -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MatkimiInfoFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMdbhistory -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DBhistoryFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMdbrequestlist -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DBrequestlistFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMhistory -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, HistoryFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMmatkimguel -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, MatkimGeulFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMmkhistory -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MKhistoryFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMmkrequestlist -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MKrequestlistFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMmypage -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, MypageFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMadddog -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, AddDogFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMdoglist -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, DogListFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMdoglist1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, DogList1Fragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMrequestlist -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, RequestListFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMupdate -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, UpdateFragment())
                    .commit()
            }
            MainActivity.ScreenItem.ITEMchatlist -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ChatListFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMmypageMKInfo -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MypageMKInfoFragment()).commit()
            }
            MainActivity.ScreenItem.ITEMmypageDBInfo -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MypageDBInfoFragment()).commit()
            }
            else -> {}
        }


    }

    // 위치 요청
    fun requestLocation() {
        try {
            // 가장 최근에 확인된 위치 알려주기
            locationClient?.lastLocation?.addOnSuccessListener {
            }
            // 위치클라이언트 만들기
            locationClient = LocationServices.getFusedLocationProviderClient(this)

            // 내위치를 요청할 때 필요한 설정값
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 30 * 1000
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
                locationRequest, locationCallback, Looper.myLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
    // 내 위치의 지도 보여주기
    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17.0f))
        Log.v("lastkingdom","showCurrentLocation")
        showMarker(curPoint)
    }
    // 마커 띄우기
    fun showMarker(curPoint: LatLng) {
        myMarker?.remove()
        MarkerOptions().also {
            it.position(curPoint)
            it.title("내위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.dogicon))

            myMarker = map.addMarker(it)
            myMarker?.tag = "1001"
        }
    }
    //게시글에서 사진 찍은거 저장하기
    fun saveFile(bitmap: Bitmap) {
        filename = dateFormat1.format(Date()) + ".jpg"
        bitmap?.apply {
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                this.compress(Bitmap.CompressFormat.JPEG, 100, it)
                it.close()
                showToast("이미지를 파일로 저장함 : ${filename}")
                uploadFile(filename!!)
            }
        }
    }
    fun uploadFile(filename: String) {
        // 저장한 파일에 대한 정보를 filePart로 만들기
        val file = File("${filesDir}/${filename}")
        val filePart = MultipartBody.Part.createFormData(
            "photo",
            filename,
            file.asRequestBody("images/jpg".toMediaTypeOrNull())
        )
        // 추가 파라미터가 있는 경우
        val params = hashMapOf<String, String>()

        //api 에 있는 리스트 조회
        BasicClient.api.uploadFile(
            file = filePart,
            params = params
        ).enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                response.body()?.output?.filename?.apply {
                    AppData.filepath = this
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
            }
        })
    }
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    // 데이트폼
    fun nowDate(): String {
        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)
        return simpleDateFormat
    }

    override fun onMapReady(p0: GoogleMap) {
    }
}
