package com.lx.project5

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.lx.api.BasicClient
import com.lx.data.MemberListResponse
import com.lx.project5.databinding.FragmentJoin2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Join2Fragment : Fragment() {
    var _binding: FragmentJoin2Binding? = null
    val binding get() = _binding!!

    var bitmap: Bitmap? = null
    var saveBitmap: Bitmap? = null

    //####
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    //앨범에서 가져오기위한 런처
    val albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        toast("앨범에서 돌아옴")

        when(it.resultCode) {
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
            AppCompatActivity.RESULT_CANCELED -> {
                toast("앨범 선택 취소")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJoin2Binding.inflate(inflater, container, false)
        initView()

        // ##### 인증 초기화, db 초기화
        mAuth = Firebase.auth
        mDbRef = Firebase.database.reference

        binding.nextButton2.setOnClickListener {
            checkPw()
        }

        binding.idCheckButton.setOnClickListener {
            checkId()
        }

        return binding.root
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

        if(registerPw.equals(pwCheck)) {
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

    //중복체크
    fun checkId() {
        var registerId = binding.registerId.text.toString()

        BasicClient.api.postMemberCheckId(
            requestCode = "1001",
            memberId = registerId
        ).enqueue(object: Callback<MemberListResponse> {
            override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>){
                val checkId = response.body()?.header?.total.toString()

                if(checkId == "1"){
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("중복체크")
                    builder.setMessage("이미 있는 아이디입니다.")
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        toast("Positive")
                    }
                    builder.show()
                    binding.registerId.setText("")
                }
                if(checkId == "0") {
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("중복체크")
                    builder.setMessage("사용 가능한 아이디입니다.")
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        toast("Positive")
                    }
                    builder.show()
                }
            }

            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {

            }

        })
    }

    //멤버리스트 추가 [파라미터]
    fun postMemberAdd(){

        var registerId = binding.registerId.text.toString()
        var registerName = binding.registerName.text.toString()
        var registerPw = binding.registerPw.text.toString()
        var registerAddress = binding.registerAddress.text.toString()
        Log.v("시발","${registerPw}")

        BasicClient.api.postMemberAdd(
            requestCode = "1001",
            memberId = registerId,
            memberPw =registerPw,
            memberName =registerName,
            memberAddress =registerAddress,
            memberImage = AppData.filepath!!

        ).enqueue(object:Callback<MemberListResponse>{
            override fun onResponse(call: Call<MemberListResponse>,response: Response<MemberListResponse>){
                Log.v("시발","postMemberAdd onResponse")
                signUp(registerId,registerPw,registerName)
                (activity as MainActivity).showToast("1")
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMlogin)
            }

            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {
                signUp(registerId,registerPw,registerName)
                Log.v("시발","postMemberAdd onFailure")
                (activity as MainActivity).showToast("2")
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMlogin)
            }

        })
    }
    //### Firebase signUp회원가입
    private fun signUp(registerId: String, registerPw: String, registerName:String) {
        Log.v("시발", "진입")
        mAuth.createUserWithEmailAndPassword(registerId, registerPw)
            .addOnCompleteListener(activity as MainActivity) { task ->
                if (task.isSuccessful) {
                    // 성공시
                    Log.v("시발", "@@@createUserWithEmail:success")
                    //#### Firebase 회원가입
                    addUserToDatabase(registerName,registerId,mAuth.currentUser?.uid!!)
                } else {
                    // 실패시
                    Log.v("시발", "@@@@createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity as MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    //#### Firebase 회원가입
    private fun addUserToDatabase(registerName: String, email: String, uId:String) {
        mDbRef.child("member").child(uId).setValue(Member(registerName,email,uId))
    }

    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}