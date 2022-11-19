package com.lx.project5.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentMkScheduleBinding


class MKScheduleFragment : Fragment() {
    var _binding: FragmentMkScheduleBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMkScheduleBinding.inflate(inflater, container, false)

        binding.mkSchduleToMatkimIngButton1.setOnClickListener {

        }




        return binding.root
    }

}