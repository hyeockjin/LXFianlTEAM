package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentCareMainBinding
import com.lx.project5.databinding.FragmentFirstBinding

class CareMainFragment : Fragment() {
    var _binding: FragmentCareMainBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCareMainBinding.inflate(inflater, container, false)



        binding.time.setText("${AppData.point} 분 남음")


        // 버튼 클릭하면 프로그래스 증가
        binding.playButton.setOnClickListener { view ->
            binding.progressBar.incrementProgressBy(50)
            AppData.point = AppData.point!! + 50
            binding.time.text = "${AppData.point} 분 남음"
        }
        binding.output4.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcareTodolist)
        }
        binding.completeButton0.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcomplete)
        }
        return binding.root
    }
}