package com.lx.project5.MyPage

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lx.api.BasicClient
import com.lx.data.MemberListResponse
import com.lx.project5.AppData.AppData
import com.lx.project5.AppData.LoginData
import com.lx.project5.MainActivity
import com.lx.project5.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!
    //Firebase 채팅 변수선언
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        //### firebase 인증 초기화
        mAuth = Firebase.auth

        binding.register.setOnClickListener {
            val curActivity = activity as MainActivity
            curActivity.onFragmentChanged(MainActivity.ScreenItem.ITEMjoin1)
        }

        binding.loginButton.setOnClickListener {
            readMember()
        }

        return binding.root
    }

    fun readMember() {
        val memberId = binding.loginId.text.toString()
        val memberPw = binding.loginPassword.text.toString()

        BasicClient.api.postMemberLogin(
            requestCode = "1001",
            memberId = memberId,
            memberPw = memberPw
        ).enqueue(object : Callback<MemberListResponse> {
            override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {
                val checkMember = response.body()?.header?.total.toString()
                println(checkMember)

                if(checkMember == "1"){
                    (activity as MainActivity).showToast("로그인 성공")
                    AppData.loginData = LoginData()


                    firebaseLogin(memberId,memberPw)
                    //(activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)

                } else if(checkMember == "0"){
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("로그인")
                    builder.setMessage("아이디/비밀번호를 다시 입력해주세요.")
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        toast("Positive")
                    }
                    builder.show()
                    binding.loginId.setText("")
                    binding.loginPassword.setText("")
                }

            }
            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("qkqh")
            }

        })

    }

    // firebaseLogin
    private fun firebaseLogin(memberId: String, memberPw: String) {
        mAuth.signInWithEmailAndPassword(memberId, memberPw)
            .addOnCompleteListener(activity as MainActivity) { task ->
                if (task.isSuccessful) {
                    // 성공시
                    Log.v("시발", "signInWithEmail:success")
                } else {
                    // 실패시 에러 발생하여 잠시 막아놓음
                    Log.v("시발", "@@@@@@signInWithEmail:failure", task.exception)
//                    Toast.makeText(activity as MainActivity, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun toast(message:String){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show()
    }

    }

