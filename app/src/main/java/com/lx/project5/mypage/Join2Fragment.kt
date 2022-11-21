package com.lx.project5.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lx.project5.MainActivity
import com.lx.project5.appdata.AppData
import com.lx.project5.appdata.ChatData
import com.lx.project5.databinding.FragmentJoin2Binding


class Join2Fragment : Fragment() {
    var _binding: FragmentJoin2Binding? = null
    val binding get() = _binding!!

    var bitmap: Bitmap? = null
    var saveBitmap: Bitmap? = null

    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    //앨범에서 가져오기위한 런처
    val albumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            toast("앨범에서 돌아옴")

            when (it.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    it.data?.apply {
                        val imageUri = this.data
                        imageUri?.let {
                            val cr = requireActivity().contentResolver
                            bitmap = MediaStore.Images.Media.getBitmap(cr, it)
                            saveBitmap = bitmap
                            binding.profileImageView.setImageBitmap(bitmap)
                            val saveCapture = activity as MainActivity
                            saveCapture.saveFile(bitmap!!)
                        }
                    }
                }
                AppCompatActivity.RESULT_CANCELED -> { toast("앨범 선택 취소") }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoin2Binding.inflate(inflater, container, false)
        initView()

        // ##### 인증 초기화, db 초기화
        mAuth = Firebase.auth
        mDbRef = Firebase.database.reference

        binding.nextButton2.setOnClickListener {
            checkPw()
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMlogin)
        }
        binding.idCheckButton.setOnClickListener {
            var memberId = binding.registerName.text.toString()
            checkId(memberId)
        }
        return binding.root
    }

    private fun checkId(memberId: String) {
        mDbRef.child("member").child( mAuth.currentUser?.uid!!)
            .child("email").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue(String::class.java)
                    Log.v("시발","개시발아${value}")
                    if (value != memberId) {
                        toast("이미 존재하는 이메일입니다")
                    } else {
                        toast("사용가능한 이메일입니다.")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.v("시발","아이디 데이터 시발 못가져옴 ")
                }
            })
    }

    //뷰 초기화
    fun initView() {
        //앨범에서 가져오기 버튼 눌렀을 때
        binding.addProfileButton2.setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
            albumIntent.type = "image/*"
            albumLauncher.launch(albumIntent)
        }

    }

    //비밀번호 확인 맞을때만 회원가입 가능하게 하기
    fun checkPw() {
        var registerPw = binding.registerPw.text.toString()
        var pwCheck = binding.pwCheck.text.toString()

        if (registerPw.equals(pwCheck)) {
            postMemberAdd()
        } else {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("회원가입")
            builder.setMessage("비밀번호를 다시 입력해주세요.")
            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                toast("Positive")
            }
            builder.show()
        }
    }

    //멤버리스트 추가 [파라미터]
    fun postMemberAdd() {

        var registerId = binding.registerId.text.toString()
        var registerName = binding.registerName.text.toString()
        var registerPw = binding.registerPw.text.toString()
        var registerAddress = binding.registerAddress.text.toString()
        var memberImage = AppData.filepath!!

        Log.v("시발", "${registerPw}")
        signUp(registerId, registerPw, registerName,memberImage, registerAddress)

    }

    //### Firebase signUp회원가입
    private fun signUp(registerId: String, registerPw: String, registerName: String ,memberImage: String, registerAddress:String) {

        mAuth.createUserWithEmailAndPassword(registerId, registerPw)
            .addOnCompleteListener(activity as MainActivity) { task ->
                if (task.isSuccessful) {
                    // 성공시
                    Log.v("시발", "@@@createUserWithEmail:success")
                    //#### Firebase 회원가입
                    addUserToDatabase(registerName, registerId, mAuth.currentUser?.uid!!,registerAddress,memberImage)
                } else {
                    // 실패시
                    Log.v("시발", "@@@@createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity as MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //#### Firebase 회원가입
    private fun addUserToDatabase(registerName: String, email: String, uId: String ,memberImage: String, registerAddress:String) {
        mDbRef.child("member").child(uId)
            .setValue(ChatData(registerName, email, uId,memberImage),registerAddress)
    }

    fun toast(message: String) {
        Toast.makeText(activity as MainActivity, message, Toast.LENGTH_LONG).show()
    }
}

