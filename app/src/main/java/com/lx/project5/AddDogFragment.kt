package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentFirstBinding

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
            val dogImage = "1"



        }

        return binding.root
    }

}