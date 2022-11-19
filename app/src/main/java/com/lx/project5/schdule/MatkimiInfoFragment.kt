package com.lx.project5.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentPetSitterInfoBinding


class MatkimiInfoFragment : Fragment() {
    var _binding: FragmentMatkimiInfoInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMatkimiInfoInfoBinding.inflate(inflater, container, false)

        binding.petSitterInfoToMatkimIng.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolboming)
        }

        return binding.root
    }

}