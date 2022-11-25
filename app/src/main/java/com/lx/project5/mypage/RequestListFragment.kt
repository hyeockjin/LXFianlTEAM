package com.lx.project5.mypage

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.R
import com.lx.project5.databinding.FragmentRequestListBinding
import org.checkerframework.checker.units.qual.C


class RequestListFragment : Fragment() {
    var _binding: FragmentRequestListBinding? = null
    val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRequestListBinding.inflate(inflater, container, false)

        binding.requestlistToMypage.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
        }

        binding.requestshowlist.setOnClickListener {
            binding.requestshowlist.setTextColor(Color.parseColor("#c990f1"))
            binding.requestshowlist2.setTextColor(Color.parseColor("#A3A3A3"))
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container2, MKrequestlistFragment()).commit()
        }

        binding.requestshowlist2.setOnClickListener {
            binding.requestshowlist2.setTextColor(Color.parseColor("#f6b278"))
            binding.requestshowlist.setTextColor(Color.parseColor("#A3A3A3"))
            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container2, DBrequestlistFragment()).commit()
        }

        return binding.root
    }



}