package com.lx.project5.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentMkhistoryBinding

class MKhistoryFragment : Fragment() {
    var _binding: FragmentMkhistoryBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMkhistoryBinding.inflate(inflater, container, false)

        binding.petSitterInfoButton1.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolbomiInfo)
        }
2
        binding.petSitterInfoButton2.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolbomiInfo)
        }

        binding.petSitterInfoButton3.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolbomiInfo)
        }


        return binding.root
    }

}