package com.lx.project5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.lx.api.BasicClient
import com.lx.data.ReviewListResponse
import com.lx.project5.databinding.FragmentCareInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareInfoFragment : Fragment() {
    var _binding: FragmentCareInfoBinding? = null
    val binding get() = _binding!!
    var reviewAdapter: ReviewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCareInfoBinding.inflate(inflater, container, false)
        initView()
        initList()
        reviewView()

        binding.backButton12.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEM1)
        }
        binding.registerButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMwrite)

        }

        return binding.root
    }

    fun initView(){
        AppData.selectedCardItem.apply{
            this?.careImage.let{
                val uri = Uri.parse("http://192.168.0.12:8001${this?.careImage}")
                Glide.with(binding.imageView2).load(uri).into(binding.imageView2)
            }
            binding.textView7.text = "${AppData.selectedCardItem?.careName}"
            binding.textView8.text = "${AppData.selectedCardItem?.careId}"
            binding.textView9.text = "${AppData.selectedCardItem?.careAddress}"

        }
    }

    // 리스트 초기화
    fun initList() {
        val layoutManager = LinearLayoutManager(context)
//        binding.reviewList.layoutManager = layoutManager

        reviewAdapter = ReviewAdapter()
//        binding.reviewList.adapter = reviewAdapter

    }

    fun reviewView() {
//        AppData.selectedCardItem.apply{
//            this?.careImage.let{
//                val uri = Uri.parse("http://192.168.0.12:8001${this?.careImage}")
//                Glide.with(binding.imageView2).load(uri).into(binding.imageView2)
//            }
//            binding.textView7.text = "${AppData.selectedCardItem?.careName}"
//            binding.textView8.text = "${AppData.selectedCardItem?.careId}"
//            binding.textView9.text = "${AppData.selectedCardItem?.careAddress}"
//
//        }
    }

}

