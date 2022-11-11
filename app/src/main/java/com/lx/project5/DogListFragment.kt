package com.lx.project5

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.project5.databinding.FragmentDogListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogListFragment : Fragment() {
    var _binding: FragmentDogListBinding? = null
    val binding get() = _binding!!

    var petAdapter: PetAdapter? = null
    val petInfoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDogListBinding.inflate(inflater, container, false)


        initList()
        petView()


        binding.addDogButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMaddDog)
        }



        return binding.root
    }

    // 리스트 초기화
    fun initList() {

        // 1. 리스트의 모양을 담당하는 것
        // (LinearLayoutManager : 아래쪽으로 아이템들이 보이는 것, GridLayoutManager : 격자 형태로 보이는 것)
        val layoutManager = LinearLayoutManager(context)
        binding.dogList.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 각 아이템의 모양을 만들어주는 것
        petAdapter = PetAdapter()
        binding.dogList.adapter = petAdapter

        // 4. 아이템을 클릭했을 때 동작할 코드 넣어주기
        petAdapter?.listener = object: OnPetItemClickListener {
            override fun onItemClick(holder: PetAdapter.ViewHolder?, view: View?, position: Int) {
                petAdapter?.apply {
                    val item = items.get(position)

                    AppData.selectedItem = item

                    val petInfoIntent = Intent(context, PetInfoFragment::class.java)
                    petInfoLauncher.launch(petInfoIntent)

                }
            }

        }

    }


    fun petView() {

        var memberNo = AppData.loginData?.memberNo
        BasicClient.api.getPetFilter(
            requestCode = "1001",
            memberNo = memberNo.toString()
        ).enqueue(object: Callback<DogListResponse> {
            override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {

                addPetList(response)
            }

            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {
            }

        })


    }

    fun addPetList(response: Response<DogListResponse>){

        petAdapter?.apply{
            response.body()?.data?.let {
                for(item in it) {
                    this.items.add(PetData(
                        item.dogNo,
                        item.memberNo,
                        item.dogName,
                        item.dogGender,
                        item.dogAge,
                        item.dogEducation,
                        item.dogCharacter,
                        item.dogBreed
                        )
                    )
                }
            }
            this.notifyDataSetChanged()

        }

    }


}