package com.lx.project5.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentPetSitterInfoBinding


class PetSitterInfoFragment : Fragment() {
    var _binding: FragmentPetSitterInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPetSitterInfoBinding.inflate(inflater, container, false)

        binding.petSitterInfoToMatkimIng.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmatkiming)
        }

        return binding.root
    }

}