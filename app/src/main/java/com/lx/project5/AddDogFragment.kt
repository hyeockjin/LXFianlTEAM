package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentFirstBinding
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
            val dogType = binding.typeInput.text.toString()
            val dogImage = "1"
            val memberNo = AppData.loginData?.memberNo?.toInt()

            BasicClient.api.getPetList(
                requestCode = "1001",
                dogName = dogName,
                dogAge = dogAge,
                productPrice = productPrice,
                productStock = productStock,
                sellerNo = sellerNo.toString(),
                productTheme = productTheme,
                lat = lat,
                lng = lng,
                productExplain =productExplain,
                filepath1 = AppData.filepath!!
            ).enqueue(object: Callback<DogListResponse> {
                override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {

                    (activity as MainActivity).filename = null

                }

                override fun onFailure(call: Call<DogListResponse>, t: Throwable) {
                    (activity as MainActivity).filename = null
                    (activity as MainActivity).onFragmentChanged(MainActivity.FragmentItem.ITEM1,null)
                    (activity as MainActivity).showToast("등록 완료")
                }
            })




        }

        return binding.root
    }

}