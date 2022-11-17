package com.lx.project5

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lx.api.BasicClient
import com.lx.data.AwrListResponse
import com.lx.data.DogListResponse
import com.lx.project5.databinding.FragmentThirdBinding
import com.lx.project5.databinding.FragmentWriteandlistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteAndListFragment : Fragment() {
    var _binding: FragmentWriteandlistBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWriteandlistBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun initView1(){
        BasicClient.api.getDogInfo(
            requestCode = "1001",
            dogNo = AppData.selectedWriteItem?.dogNo.toString()
        ).enqueue(object: Callback<DogListResponse> {
            override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {


            }

            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {
            }

        })

    }



    fun initView2(){
        AppData.selectedWriteItem?.apply{
            this.memberImage?.let{
                val uri = Uri.parse("http://192.168.0.15:8001${memberImage}")
                Glide.with(binding.imageView2).load(uri).into(binding.imageView2)
            }
            binding.memberName.text = AppData.loginData?.memberId
            binding.address.text = AppData.loginData?.memberName
            binding.textView9.text = AppData.loginData?.memberAddress

        }
    }

}