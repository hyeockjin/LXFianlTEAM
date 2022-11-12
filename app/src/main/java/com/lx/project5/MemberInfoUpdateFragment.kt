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
        val memberNo = AppData.loginData?.memberNo
        val memberPw1 = binding.checkPw.text.toString()
        val memberPw2 = AppData.loginData?.memberPw
        val memberImage = "1"
        val memberAddress = binding.updateAddress.text.toString()
        val memberName = binding.updateName.text.toString()
        (activity as MainActivity).showToast(memberNo!!)
        if (memberPw1.equals(memberPw2)){
            BasicClient.api.postMemberUpdate(
                requestCode = "1001",
                memberNo = memberNo,
                memberName = memberName,
                memberAddress = memberAddress,
                memberImage = memberImage
            ).enqueue(object : Callback<MemberListResponse> {
                override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {
                    (activity as MainActivity).showToast("수정되었습니다.")
                    AppData.loginData?.memberName = memberName
                    AppData.loginData?.memberAddress = memberAddress
                }
                override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {
                    (activity as MainActivity).showToast("2")
                    (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
                }

            })
        }else {
            (activity as MainActivity).showToast("비밀번호가 틀립니다.")

        }

    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}