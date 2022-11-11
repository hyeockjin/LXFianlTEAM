package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.project5.AppData.Companion.loginData
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
            addPet()

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
        val dogImage = "1"
        val memberNo = loginData?.memberNo.toString()



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
                (activity as MainActivity).showToast("등록 완료1")


            }

            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {
                binding.nameInput.setText(t.message)

                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdogList)
            }
        })

    }

}