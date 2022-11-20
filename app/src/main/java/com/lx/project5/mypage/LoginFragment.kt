package com.lx.project5.mypage

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
import com.lx.project5.MainActivity
import com.lx.project5.appdata.AppData
import com.lx.project5.appdata.LoginData
import com.lx.project5.appdata.MemberData
import com.lx.project5.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!
    //Firebase 채팅 변수선언
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        Log.v("시발", "로그인프래그먼트 진입")

        //### firebase 인증 초기화
        mAuth = Firebase.auth
        //회원가입버튼
        binding.register.setOnClickListener {
            val curActivity = activity as MainActivity
            curActivity.onFragmentChanged(MainActivity.ScreenItem.ITEMjoin1)
        }
        //로그인버튼
        binding.loginButton.setOnClickListener {
            val loginID=binding.loginId.text.toString()
            var loginPW=binding.loginPassword.text.toString()
            firebaseLogin(loginID,loginPW)
        }

        return binding.root
    }

    // firebaseLogin 성공시 마이페이지 이동
    private fun firebaseLogin(memberId: String, memberPw: String) {

        mAuth.signInWithEmailAndPassword(memberId, memberPw)
            .addOnCompleteListener(activity as MainActivity) { task ->
                if (task.isSuccessful) {
                    // 성공시
                    Log.v("시발", "로그인 성공")

                    AppData.memberData = MemberData()
                    AppData.memberData?.memberId = memberId
                    (activity as MainActivity)?.onFragmentChanged(MainActivity.ScreenItem.ITEMmypage)
                } else {
                    Toast.makeText(activity as MainActivity, "로그인 실패!", Toast.LENGTH_SHORT).show()
                }
        }
    }

}

