package com.lx.project5

import android.R
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.lx.api.BasicClient
import com.lx.data.ReviewListResponse
import com.lx.project5.databinding.FragmentAssessBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AssessFragment : Fragment() {
    var _binding: FragmentAssessBinding? = null
    val binding get() = _binding!!






    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAssessBinding.inflate(inflater, container, false)
        setView()

        //별점 레이팅바
        binding.rtb.setOnRatingBarChangeListener { ratingBar, fl, b ->
            binding.tvRating.text = fl.toString()
        }

        binding.completeButton4.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcomplete)
        }

        binding.button2.setOnClickListener {
            reviewSave()
            reviewAdd()
        }

        return binding.root
    }

    fun reviewAdd() {

        BasicClient.api.memberReview(
            requestCode = "1001",
            memberNo = AppData.loginData?.memberNo.toString(),
            careNo = ReviewSaveData.savecareNo.toString(),
            reviewTitle = binding.reviewTitle.text.toString(),
            reviewContent = binding.reviewContent.text.toString(),
            star = ReviewSaveData.saveStar.toString()

        ).enqueue(object : Callback<ReviewListResponse> {
            override fun onResponse(call: Call<ReviewListResponse>, response: Response<ReviewListResponse>) {
                (activity as MainActivity).showToast("1")


            }
            override fun onFailure(call: Call<ReviewListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("2")

            }

        })

    }

    fun reviewSave(){
        ReviewSaveData.saveReviewTitle = binding.reviewTitle.text.toString()
        ReviewSaveData.saveReviewContent = binding.reviewContent.text.toString()
        ReviewSaveData.saveStar = binding.rtb.toString()
        (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcomplete)
    }

    fun setView(){
        AppData?.memberdata.apply{
            this?.memberImage?.let {
                val uri = Uri.parse("http://192.168.0.215:8001${memberImage}")
                Glide.with(binding.personProfile).load(uri).into(binding.personProfile)
            }
            binding.personName.setText(AppData.loginData?.memberName)

        }
    }



}