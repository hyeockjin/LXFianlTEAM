package com.lx.project5.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentMypageBinding


class MypageFragment : Fragment() {
    var _binding: FragmentMypageBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        // 여기 수정해야함
        binding.mypagetomain.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
        }


        binding.updateButton.setOnClickListener {

        }

        binding.requestButton.setOnClickListener {

        }

        binding.listhistoryListButton.setOnClickListener {

        }




        return binding.root
    }

}