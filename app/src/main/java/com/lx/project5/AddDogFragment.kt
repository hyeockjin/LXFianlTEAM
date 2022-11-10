package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.project5.databinding.FragmentAddDogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDogFragment : Fragment() {
    var _binding: FragmentAddDogBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddDogBinding.inflate(inflater, container, false)



        binding.addButton.setOnClickListener {
            val dogName = binding.nameInput.text.toString()
            val dogAge = binding.ageInput.text.toString()
            val dogGender = binding.genderInput.text.toString()
            val dogEducation = binding.input1.text.toString()
            val dogCharacter = binding.input2.text.toString()
            val dogBreed = binding.typeInput.text.toString()
            val dogImage = "1"
            val memberNo = AppData.loginData?.memberNo

            BasicClient.api.petAdd(
                requestCode = "1001",
                memberNo = memberNo,
                dogImage = dogImage,
                dogBreed = dogBreed,
                dogCharacter = dogCharacter,
                dogEducation = dogEducation,
                dogGender = dogGender,
                dogAge = dogAge,
                dogName = dogName
            ).enqueue(object: Callback<DogListResponse> {
                override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {



                }

                override fun onFailure(call: Call<DogListResponse>, t: Throwable) {


                    (activity as MainActivity).showToast("등록 완료")
                }
            })




        }

        return binding.root
    }

}