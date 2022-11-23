package com.lx.project5.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentDbrequestlistBinding


class DBrequestlistFragment : Fragment() {
    var _binding: FragmentDbrequestlistBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDbrequestlistBinding.inflate(inflater, container, false)

        binding.matkimiInfo1.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmypageMKInfo)
        }

        binding.request3.setOnClickListener {
            toast("수락되었습니다.")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


}