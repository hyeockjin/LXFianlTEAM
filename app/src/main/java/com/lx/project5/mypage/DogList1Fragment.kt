package com.lx.project5.mypage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.project5.MainActivity
import com.lx.project5.appdata.AppData
import com.lx.project5.databinding.FragmentDogList1Binding
import com.lx.project5.databinding.FragmentDogListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogList1Fragment : Fragment() {
    var _binding: FragmentDogList1Binding? = null
    val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDogList1Binding.inflate(inflater, container, false)

        binding.addDogButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMadddog)
        }

        binding.addDogToMyPage.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            binding.container7
        }, 1000)


        return binding.root
    }


    }





