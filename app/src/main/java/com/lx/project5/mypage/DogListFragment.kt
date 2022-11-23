package com.lx.project5.mypage

import android.content.Intent
import android.os.Bundle
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
import com.lx.project5.databinding.FragmentDogListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogListFragment : Fragment() {
    var _binding: FragmentDogListBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDogListBinding.inflate(inflater, container, false)

        binding.addDogButton1.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMadddog)
        }

        binding.backButton1.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
        }


        return binding.root
    }


    }





