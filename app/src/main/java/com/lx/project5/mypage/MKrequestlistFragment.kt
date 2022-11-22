package com.lx.project5.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentMkrequestlistBinding


class MKrequestlistFragment : Fragment() {
    var _binding: FragmentMkrequestlistBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMkrequestlistBinding.inflate(inflater, container, false)

        binding.PetSitterInfo4.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolbomiInfo)
        }
        binding.PetSitterInfo5.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolbomiInfo)
        }
        binding.PetSitterInfo6.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdolbomiInfo)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}