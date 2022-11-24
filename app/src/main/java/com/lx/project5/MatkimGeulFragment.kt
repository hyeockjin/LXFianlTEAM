package com.lx.project5

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lx.api.BasicClient
import com.lx.data.AwrListResponse
import com.lx.project5.appdata.GeulSaveData
import com.lx.project5.databinding.FragmentMatkimGeulBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MatkimGeulFragment : Fragment() {
    var _binding: FragmentMatkimGeulBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMatkimGeulBinding.inflate(inflater, container, false)


        //지도로 가기
        binding.locationButton.setOnClickListener {
            val locationIntent= Intent(activity,LocalActivity::class.java)
            startActivity(locationIntent)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.locationView.text = "위치정보 등록완료"
            }, 1000)

        }

        binding.jakseonghagi.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEM1)
            showToast("맡김글이 작성되었습니다.")
        }

        binding.matkimgeulToMain.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEM1)
        }

        return binding.root
    }



    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}