package com.lx.project5.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentMatkimIngBinding


class MatkimIngFragment : Fragment() {
    var _binding: FragmentMatkimIngBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMatkimIngBinding.inflate(inflater, container, false)

        binding.matkimIngToMKSchedule.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmkschedule)
        }

        binding.petSitterInfoButton.setOnClickListener {

        }

        return binding.root
    }

}