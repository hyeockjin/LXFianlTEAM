package com.lx.project5.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.R
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentMkScheduleBinding
import com.lx.project5.databinding.FragmentScheduleBinding
import com.lx.project5.mypage.MKhistoryFragment


class ScheduleFragment : Fragment() {
    var _binding: FragmentScheduleBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container3, MKScheduleFragment()).commit()


        binding.matkimiSchedule1.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container3, MKScheduleFragment()).commit()
        }

        binding.matkimiSchedule2.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container3, DBScheduleFragment()).commit()
        }



        return binding.root
    }

}