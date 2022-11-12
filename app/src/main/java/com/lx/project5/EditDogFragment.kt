package com.lx.project5

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.data.MemberListResponse
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentEditDogBinding
import com.lx.project5.databinding.FragmentFirstBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDogFragment : Fragment() {
    var _binding: FragmentEditDogBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditDogBinding.inflate(inflater, container, false)

        binding.nameOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogName}")
        binding.ageOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogAge}")
        binding.genderOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogGender}")
        binding.typeOutput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogBreed}")
        binding.educationInput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogEducation}")
        binding.characterInput2.text = Editable.Factory.getInstance().newEditable("${AppData.selectedItem?.dogCharacter}")

        binding.editButton.setOnClickListener {
            editDog()
        }


        return binding.root
    }
    fun editDog(){
        val dogAge = binding.ageOutput2.getText().toString()
        val dogCharacter = binding.characterInput2.getText().toString()
        val dogEducation = binding.educationInput2.getText().toString()
        val dogBreed = binding.typeOutput2.getText().toString()
        val dogGender = binding.genderOutput2.getText().toString()
        val dogImage = "1"
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
            dogImage = dogImage,
            dogName = dogName,
            dogNo = dogNo,
            memberNo = memberNo
        ).enqueue(object : Callback<DogListResponse> {
            override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {

            }
            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {

                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
            }
        })
    }



}