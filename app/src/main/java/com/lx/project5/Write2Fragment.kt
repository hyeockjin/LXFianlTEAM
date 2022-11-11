package com.lx.project5

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.acrListResponse
import com.lx.data.awrListResponse
import com.lx.project5.databinding.FragmentWrite2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Write2Fragment : Fragment() {
    var _binding: FragmentWrite2Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWrite2Binding.inflate(inflater, container, false)

        //지도로 가기
        binding.locationButton1.setOnClickListener {
            val locationIntent= Intent(activity,LocalActivity::class.java)
            startActivity(locationIntent)
        }
        //등록하기 버튼
        binding.editButton2.setOnClickListener {
            awrAdd()
        }




        return binding.root


    }

    fun awrAdd() {
        //받아야하는거거 로그인 한거 넘버 돌봄이 버 체크한거
        // 달력 이거 데이트 폼이랑 정보 받아오는거 모르겠어요 알려주세요 ~
        val lat = AppData.lat?.toDouble()
        val lng = AppData.lng?.toDouble()

        BasicClient.api.awrAdd(
            requestCode = "1001",
            memberNo = "3",
            startTime = "2022-11-11 00:00:00",
            endTime = "2022-11-11 00:00:00",
            writeTime = "2022-11-11 00:00:00",
            assignTitle = "123123",
            assignContent = "123123123",
            lat = lat!!,
            lng = lng!!

        ).enqueue(object : Callback<awrListResponse> {
            override fun onResponse(call: Call<awrListResponse>, response: Response<awrListResponse>) {
                (activity as MainActivity).showToast("1")


            }
            override fun onFailure(call: Call<awrListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("2")
            }

        })

    }

}