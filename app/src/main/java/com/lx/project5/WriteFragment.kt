package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.AcrListResponse
import com.lx.project5.databinding.FragmentWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WriteFragment : Fragment() {
    var _binding: FragmentWriteBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)



        binding.editButton2.setOnClickListener {

            acrAdd()

        }
        return binding.root


    }

    fun acrAdd() {
        //받아야하는거거 로그인 한거 넘버 돌봄이 버 체크한거
        // 달력 이거 데이트 폼이랑 정보 받아오는거 모르겠어요 알려주세요 ~
        val memberId = binding
        val memberPw = binding

        BasicClient.api.acrAdd(
            requestCode = "1001",
            memberNo = "3",
            careNo = "3",
            dogNo = "3",
            startTime = "2022-11-11 00:00:00",
            endTime = "2022-11-11 00:00:00",
            assignTitle = "123123",
            assignContent = "123123123"

        ).enqueue(object : Callback<AcrListResponse> {
            override fun onResponse(call: Call<AcrListResponse>, response: Response<AcrListResponse>) {
                (activity as MainActivity).showToast("1")


            }
            override fun onFailure(call: Call<AcrListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("2")
            }

        })

    }

}