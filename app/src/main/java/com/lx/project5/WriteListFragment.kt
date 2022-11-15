package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.api.BasicClient
import com.lx.data.AwrListResponse
import com.lx.project5.databinding.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteListFragment : Fragment() {
    var _binding: FragmentWriteListBinding? = null
    val binding get() = _binding!!
    var writeAdapter: WriteAdapter? = null
    val writeInfoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWriteListBinding.inflate(inflater, container, false)

        initList()
        writeView()
        
        binding.backButton5.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
        }

        return binding.root
    }
    // 리스트 초기화
    fun initList() {

        // 1. 리스트의 모양을 담당하는 것
        // (LinearLayoutManager : 아래쪽으로 아이템들이 보이는 것, GridLayoutManager : 격자 형태로 보이는 것)
        val layoutManager = LinearLayoutManager(context)
        binding.writeList.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 각 아이템의 모양을 만들어주는 것
        writeAdapter = WriteAdapter()
        binding.writeList.adapter = writeAdapter

        // 4. 아이템을 클릭했을 때 동작할 코드 넣어주기
//        writeAdapter?.listener = object: OnWriteItemClickListener {
//            override fun onItemClick(holder: PetAdapter.ViewHolder?, view: View?, position: Int) {
//                writeAdapter?.apply {
//                    val item = items.get(position)
//
//                    AppData.selectedWriteItem = item
//
//                    val petInfoIntent = Intent(context, PetInfoFragment::class.java)
//                    petInfoLauncher.launch(petInfoIntent)
//
//                }
//            }
//
//        }

    }


    fun writeView() {

        var memberNo = AppData.loginData?.memberNo
        BasicClient.api.getawrFilter(
            requestCode = "1001",
            memberNo = memberNo.toString()
        ).enqueue(object: Callback<AwrListResponse> {
            override fun onResponse(call: Call<AwrListResponse>, response: Response<AwrListResponse>) {

                addWriteList(response)
            }

            override fun onFailure(call: Call<AwrListResponse>, t: Throwable) {
            }

        })


    }

    fun addWriteList(response: Response<AwrListResponse>){

        writeAdapter?.apply{
            response.body()?.data?.let {
                for(item in it) {
                    this.items.add(WriteData(
                        item.writeTime,
                        item.lng,
                        item.lat,
                        item.awrn,
                        item.assignTitle,
                        item.endTime,
                        item.startTime,
                        item.memberNo,
                        item.assignContent,
                        item.dogNo
                        )
                    )
                }
            }
            this.notifyDataSetChanged()

        }

    }

}


