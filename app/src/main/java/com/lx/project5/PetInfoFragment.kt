package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.AppData.Companion.selectedItem
import com.lx.project5.databinding.FragmentMemberInfoUpdateBinding
import com.lx.project5.databinding.FragmentPetInfoBinding

class PetInfoFragment : Fragment() {
    var _binding: FragmentPetInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPetInfoBinding.inflate(inflater, container, false)

        binding.petName.text = "${selectedItem?.dogName}"
        binding.petAge.text = "${selectedItem?.dogAge}"
        binding.petGender.text = "${selectedItem?.dogGender}"
        binding.petBreed?.text = "${selectedItem?.dogBreed}"
        binding.petEducation.text = "${selectedItem?.dogEducation}"
        binding.petCharacter.text = "${selectedItem?.dogCharacter}"

        binding.editInfoButton2.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMeditDog)
        }

        binding.backButton3.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdogList)
        }

        return binding.root
    }

}