package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentAssessBinding
import com.lx.project5.databinding.FragmentFirstBinding

class AssessFragment : Fragment() {
    var _binding: FragmentAssessBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAssessBinding.inflate(inflater, container, false)

        binding.completeButton3.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcomplete)
        }

        binding.completeButton4.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcomplete)
        }

        return binding.root
    }

}