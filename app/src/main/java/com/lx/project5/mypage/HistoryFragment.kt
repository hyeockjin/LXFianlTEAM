package com.lx.project5.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    var _binding: FragmentHistoryBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }

}