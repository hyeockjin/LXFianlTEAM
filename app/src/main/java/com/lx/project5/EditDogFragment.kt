package com.lx.project5

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.project5.databinding.FragmentEditDogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDogFragment : Fragment() {
    var _binding: FragmentEditDogBinding? = null
    val binding get() = _binding!!
    var bitmap: Bitmap? = null

    //사진찍기 런처
    val captureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        showToast("사진찍고 돌아옴")

        when(it.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                bitmap = it.data?.extras?.get("data") as Bitmap
                binding.imageView30.setImageBitmap(bitmap)
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
                        binding.imageView30.setImageBitmap(bitmap)
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
        _binding = FragmentEditDogBinding.inflate(inflater, container, false)

        initView()

        binding.editButton.setOnClickListener {
            editDog()
        }
        //앨범에서 가져오기 버튼 눌렀을 때
        binding.photoButton2.setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
            albumIntent.type = "image/*"
            albumLauncher.launch(albumIntent)
        }


        return binding.root
    }
    fun editDog(){
        val dogAge = binding.ageOutput2.getText().toString()
        val dogCharacter = binding.characterInput2.getText().toString()
        val dogEducation = binding.educationInput2.getText().toString()
        val dogBreed = binding.typeOutput2.getText().toString()
        val dogGender = binding.genderOutput2.getText().toString()
        val dogImage = AppData.filepath
        val dogName= binding.nameOutput2.getText().toString()
        val dogNo = AppData.selectedItem?.dogNo.toString()
        val memberNo= AppData.selectedItem?.memberNo.toString()




        BasicClient.api.postDogUpdate(
            requestCode = "1001",
            dogAge = dogAge,
            dogCharacter = dogCharacter,
            dogEducation = dogEducation,
            dogBreed = dogBreed,
            dogGender = dogGender,
            dogImage = dogImage!!,
            dogName = dogName,
            dogNo = dogNo,
            memberNo = memberNo
        ).enqueue(object : Callback<DogListResponse> {
            override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {

            }
            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {
                (activity as MainActivity).filename = null
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
            }
        })
    }
    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
    fun initView(){
        AppData.selectedItem?.apply{
            this.dogImage?.let{
                val uri = Uri.parse("http://172.168.10.3:8001${this.dogImage}")
                Glide.with(binding.imageView30).load(uri).into(binding.imageView30)
            }
            binding.nameOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogName}")
            binding.ageOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogAge}")
            binding.genderOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogGender}")
            binding.typeOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogBreed}")
            binding.educationInput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogEducation}")
            binding.characterInput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogCharacter}")


        }
    }



}