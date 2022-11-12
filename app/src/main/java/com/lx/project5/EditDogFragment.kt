package com.lx.project5

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentEditDogBinding
import com.lx.project5.databinding.FragmentFirstBinding

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



        return binding.root
    }

}