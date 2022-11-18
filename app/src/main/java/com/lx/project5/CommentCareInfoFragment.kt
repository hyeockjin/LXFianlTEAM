package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.AwcResponse
import com.lx.data.CareListResponse
import com.lx.data.DogListResponse
import com.lx.project5.databinding.FragmentCommentCareInfoBinding
import com.lx.project5.databinding.FragmentThirdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentCareInfoFragment : Fragment() {
    var _binding: FragmentCommentCareInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCommentCareInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun ifitView(){
        AppData.selectedCommentItem?.careNo.toString()
        BasicClient.api.getcareInfo(
            requestCode = "1001",
            careNo = "1"
        ).enqueue(object: Callback<CareListResponse> {
            override fun onResponse(call: Call<CareListResponse>, response: Response<CareListResponse>) {


            }

            override fun onFailure(call: Call<CareListResponse>, t: Throwable) {
            }

        })

    }

}