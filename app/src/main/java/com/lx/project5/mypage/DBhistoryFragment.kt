package com.lx.project5.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentDbhistoryBinding


class DBhistoryFragment : Fragment() {
    var _binding: FragmentDbhistoryBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDbhistoryBinding.inflate(inflater, container, false)
//        binding.matkimiInfoButton1.setOnClickListener {
//            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmatkimiInfo)
//        }
//        binding.matkimiInfoButton2.setOnClickListener {
//            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmatkimiInfo)
//        }
//        binding.matkimiInfoButton3.setOnClickListener {
//            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmatkimiInfo)
//        }




        return binding.root
    }

}