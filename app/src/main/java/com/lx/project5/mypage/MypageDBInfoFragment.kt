package com.lx.project5.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.R
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentMypageDbInfoBinding

class MypageDBInfoFragment : Fragment() {
    var _binding: FragmentMypageDbInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageDbInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

}