package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentWriteBinding


class WriteFragment : Fragment() {
    var _binding: FragmentWriteBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)



        binding.editButton2.setOnClickListener {

        sendRequest()

        }
        return binding.root


    }

    fun sendRequest() {
        //받아야하는거거 로그인 한거 넘버 돌봄이 버 체크한거
        // 달력 이거 데이트 폼이랑 정보 받아오는거 모르겠어요 알려주세요 ~

    }

}