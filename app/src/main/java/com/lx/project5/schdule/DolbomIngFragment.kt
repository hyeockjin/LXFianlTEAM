package com.lx.project5.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.appdata.AppData
import com.lx.project5.databinding.FragmentDolbomIngBinding


class DolbomIngFragment : Fragment() {
    var _binding: FragmentDolbomIngBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDolbomIngBinding.inflate(inflater, container, false)

        binding.dolbomIngToDBSchedule.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdbschedule)
        }

        binding.matkimiInfoButton.setOnClickListener {

        }

        binding.startWalkButton.setOnClickListener {

        }

        binding.finishWalkButton.setOnClickListener {

        }

        return binding.root
    }

}