package com.lx.project5.mypage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.R
import com.lx.project5.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    var _binding: FragmentHistoryBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        binding.historyTomypage.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
        }

        binding.reqshowhis1.setOnClickListener {
            binding.reqshowhis1.setTextColor(Color.parseColor("#c990f1"))
            binding.reqshowhis2.setTextColor(Color.parseColor("#A3A3A3"))
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container1, MKhistoryFragment()).commit()
        }

        binding.reqshowhis2.setOnClickListener {
            binding.reqshowhis2.setTextColor(Color.parseColor("#f6b278"))
            binding.reqshowhis1.setTextColor(Color.parseColor("#A3A3A3"))
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container1, DBhistoryFragment()).commit()
        }


        return binding.root
    }
}