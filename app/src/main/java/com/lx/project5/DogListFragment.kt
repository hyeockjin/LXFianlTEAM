package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentCareInfoBinding
import com.lx.project5.databinding.FragmentDogListBinding

class DogListFragment : Fragment() {
    var _binding: FragmentDogListBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDogListBinding.inflate(inflater, container, false)

        binding.addDogButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMaddDog)
        }

        return binding.root
    }

}