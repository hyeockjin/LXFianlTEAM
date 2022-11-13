package com.lx.project5

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.project5.AppData.Companion.loginData
import com.lx.project5.DogSaveData.Companion.dogBitmap
import com.lx.project5.DogSaveData.Companion.dogImage
import com.lx.project5.databinding.FragmentAddDogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDogFragment : Fragment() {
    var _binding: FragmentAddDogBinding? = null
    val binding get() = _binding!!
    var bitmap: Bitmap? = null

    //사진찍기 런처
    val captureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        showToast("사진찍고 돌아옴")

        when(it.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                bitmap = it.data?.extras?.get("data") as Bitmap
                dogBitmap = bitmap
                binding.profileImageView2.setImageBitmap(bitmap)
                val saveCapture = activity as MainActivity
                saveCapture.saveFile(bitmap!!)


            }
            AppCompatActivity.RESULT_CANCELED -> {
                showToast("취소함")
            }
        }
    }

    //앨범에서 가져오기위한 런처
    val albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        showToast("앨범에서 돌아옴")

        when(it.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                it.data?.apply {
                    val imageUri = this.data
                    imageUri?.let {
                        val cr = requireActivity().contentResolver
                        bitmap = MediaStore.Images.Media.getBitmap(cr, it)
                        dogBitmap = bitmap
                        binding.profileImageView2.setImageBitmap(bitmap)
                        val saveCapture = activity as MainActivity
                        saveCapture.saveFile(bitmap!!)
                    }
                }
            }
            AppCompatActivity.RESULT_CANCELED -> {
                showToast("앨범 선택 취소")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddDogBinding.inflate(inflater, container, false)


        binding.addButton.setOnClickListener {
            addPet()

        }
        //앨범에서 가져오기 버튼 눌렀을 때
        binding.photoButton.setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
            albumIntent.type = "image/*"
            albumLauncher.launch(albumIntent)
        }

        return binding.root
    }

    fun addPet() {
        val dogName = binding.nameInput.text.toString()
        val dogAge = binding.ageInput.text.toString()
        val dogGender = binding.genderInput.text.toString()
        val dogBreed = binding.breedInput.text.toString()
        val dogEducation = binding.educationInput.text.toString()
        val dogCharacter = binding.characterInput.text.toString()
        val dogImage = AppData.filepath
        val memberNo = loginData?.memberNo.toString()



        BasicClient.api.petAdd(
            requestCode = "1001",
            memberNo = memberNo,
            dogImage = dogImage!!,
            dogBreed = dogBreed,
            dogCharacter = dogCharacter,
            dogEducation = dogEducation,
            dogGender = dogGender,
            dogAge = dogAge,
            dogName = dogName
        ).enqueue(object: Callback<DogListResponse> {
            override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {
                (activity as MainActivity).showToast("등록 완료1")
                (activity as MainActivity).filename = null


            }

            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {
                (activity as MainActivity).filename = null

                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdogList)
            }
        })

    }
    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}