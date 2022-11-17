package com.lx.project5

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lx.project5.AppData.Companion.selectedItem
import com.lx.project5.databinding.FragmentMemberInfoUpdateBinding
import com.lx.project5.databinding.FragmentPetInfoBinding

class PetInfoFragment : Fragment() {
    var _binding: FragmentPetInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPetInfoBinding.inflate(inflater, container, false)

        initView()

        binding.editInfoButton2.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMeditDog)
        }

        binding.backButton3.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdogList)
        }

        return binding.root
    }
    fun initView(){
        AppData.selectedItem?.apply{
            this.dogImage .let{
                val uri = Uri.parse("http://172.30.1.3:8001${dogImage}")
                Glide.with(binding.imageView4).load(uri).into(binding.imageView4)
            }
            binding.petName.text = "${selectedItem?.dogName}"
            binding.petAge.text = "${selectedItem?.dogAge}"
            binding.petGender.text = "${selectedItem?.dogGender}"
            binding.petBreed?.text = "${selectedItem?.dogBreed}"
            binding.petEducation.text = "${selectedItem?.dogEducation}"
            binding.petCharacter.text = "${selectedItem?.dogCharacter}"

        }
    }

}