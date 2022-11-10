package com.lx.project5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lx.api.BasicClient
import com.lx.data.MemberListResponse
import com.lx.project5.AppData.Companion.loginData
import com.lx.project5.databinding.FragmentMemberInfoUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberInfoUpdateFragment : Fragment() {
    var _binding: FragmentMemberInfoUpdateBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, asavedInstanceState: Bundle?): View? {
        _binding = FragmentMemberInfoUpdateBinding.inflate(inflater, container, false)

        binding.infoUpdateButton.setOnClickListener {
            updateMember()

        }

        return binding.root
    }

    //회원 정보 수정 요청하기
    fun updateMember() {
        val memberId = binding.inputId.text.toString()
        val memberPw = binding.inputPw.text.toString()
        val memberImage = binding.inputImageView.textAlignment.toString()

        BasicClient.api.postMemberUpdate(
            requestCode = "1001",
            memberId = memberId,
            memberPw = memberPw,
            memberImage = memberImage
        ).enqueue(object : Callback<MemberListResponse> {
            override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {

            }
            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
                showToast("수정 성공")
                loginData?.memberId = ""
                loginData?.memberName = ""
                loginData?.memberImage = ""
            }

        })

    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}