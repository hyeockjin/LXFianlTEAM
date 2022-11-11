package com.lx.project5

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.AwrListResponse
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

        ).enqueue(object : Callback<AwrListResponse> {
            override fun onResponse(call: Call<AwrListResponse>, response: Response<AwrListResponse>) {
                (activity as MainActivity).showToast("${lat}, ${lng}")
                (activity as MainActivity).showToast("1")


            }
            override fun onFailure(call: Call<AwrListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("2")
            }

        })

    }

}