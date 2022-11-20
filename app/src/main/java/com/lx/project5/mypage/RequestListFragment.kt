package com.lx.project5.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentRequestListBinding


class RequestListFragment : Fragment() {
    var _binding: FragmentRequestListBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRequestListBinding.inflate(inflater, container, false)

        binding.requesttomypage.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
        }

        binding.requestshowlist.setOnClickListener {

        }

        binding.requestshowlist2.setOnClickListener {

        }

        return binding.root
    }

}