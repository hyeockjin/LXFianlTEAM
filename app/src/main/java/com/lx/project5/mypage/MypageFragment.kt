package com.lx.project5.mypage

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.lx.project5.MainActivity
import com.lx.project5.appdata.AppData
import com.lx.project5.databinding.FragmentMypageBinding


class MypageFragment : Fragment() {
    var _binding: FragmentMypageBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        initView()

        // 여기 수정해야함
        binding.mypagetomain.setOnClickListener {
            //(activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
        }
        binding.updateButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmatkimiInfo)
        }
        binding.requestButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMrequestlist)
        }
        binding.listhistoryListButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMhistory)
        }
        return binding.root
    }

    fun initView(){
        AppData.memberData?.apply{
            this.memberImage?.let{
                val uri = Uri.parse("http://192.168.0.10:8001${memberImage}")
                Glide.with(binding.imageView2).load(uri).into(binding.imageView2)
            }
            binding.memberName.text = AppData.memberData?.memberId
            binding.memberTel.text = AppData.memberData?.memberAddress
        }
    }

}