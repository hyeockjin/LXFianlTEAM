package com.lx.project5.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.R
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentMypageMkInfoBinding

class MypageMKInfoFragment : Fragment() {
    var _binding: FragmentMypageMkInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageMkInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

}