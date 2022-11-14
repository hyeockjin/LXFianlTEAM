package com.lx.project5

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    var _binding: FragmentMyPageBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        initView()





        binding.updateButton.setOnClickListener {
            val curActivity = activity as MainActivity
            curActivity.onFragmentChanged(MainActivity.ScreenItem.ITEMupdate)
        }

        binding.myWriteButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMwriteList)
        }

        binding.goDogList.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdogList)
        }
        return binding.root
    }
    fun initView(){
        AppData.loginData?.apply{
            this.memberImage?.let{
                val uri = Uri.parse("http://172.168.10.27:8001${memberImage}")
                Glide.with(binding.imageView2).load(uri).into(binding.imageView2)
            }
            binding.memberName.text = AppData.loginData?.memberId
            binding.address.text = AppData.loginData?.memberName
            binding.textView9.text = AppData.loginData?.memberAddress

        }
    }

}