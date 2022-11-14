package com.lx.project5

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.lx.api.BasicClient
import com.lx.data.AwrListResponse
import com.lx.project5.Write2SaveData.Companion.savelat
import com.lx.project5.Write2SaveData.Companion.savelng
import com.lx.project5.databinding.FragmentWrite2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Write2Fragment : Fragment(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var _binding: FragmentWrite2Binding? = null
    val binding get() = _binding!!





    //달력
    var day = 0
    var month = 0
    var year =0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear =0
    var savedHour = 0
    private var savedMinute = 0

    var cIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWrite2Binding.inflate(inflater, container, false)

        writeShow()


        pickDate() //달력

        //지도로 가기
        binding.locationButton1.setOnClickListener {
            writeSave()
            val locationIntent= Intent(activity,LocalActivity::class.java)
            startActivity(locationIntent)
        }
        //등록하기 버튼
        binding.editButton5.setOnClickListener {

            awrAdd()
        }

        //강아지 선택
        binding.setDogButton.setOnClickListener {
            AppData.dogListIndex = 2
            writeSave()
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMwriteSelect)
        }




        return binding.root


    }

    fun writeShow() {
        binding.myDog.text = Write2SaveData.savedogName
        binding.locationView.text = "${Write2SaveData.savelat.toString()}, ${Write2SaveData.savelng.toString()}"
        binding.editTextTime3.text = "${Write2SaveData.savestartTime} 시"
        binding.editTextTime4.text = "${Write2SaveData.saveendTime} 시"
        binding.editTextTextPersonName.setText(Write2SaveData.saveassignTitle)
        binding.detail1.setText(Write2SaveData.saveassignContent)
        (activity as MainActivity).showToast(AppData.lat.toString())
    }
    fun writeSave(){
        Write2SaveData.savedogName = binding.myDog.text.toString()
        Write2SaveData.saveassignTitle = binding.editTextTextPersonName.text.toString()
        Write2SaveData.saveassignContent = binding.detail1.text.toString()
    }

    fun awrAdd() {
        val lat = Write2SaveData.savelat.toString()
        val lng = Write2SaveData.savelng.toString()
        val assignTitle = binding.editTextTextPersonName.text.toString()
        val assignContent = binding.detail1.text.toString()

        BasicClient.api.awrAdd(
            requestCode = "1001",
            memberNo = AppData.loginData?.memberNo.toString(),
            dogNo = Write2SaveData.savedogNo.toString(),
            startTime = Write2SaveData.savestartTime.toString(),
            endTime = Write2SaveData.saveendTime.toString(),
            writeTime = (activity as MainActivity).nowDate(),
            assignTitle = assignTitle,
            assignContent = assignContent,
            lat = lat!!,
            lng = lng!!

        ).enqueue(object : Callback<AwrListResponse> {
            override fun onResponse(call: Call<AwrListResponse>, response: Response<AwrListResponse>) {
                (activity as MainActivity).showToast("1")
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMwriteList)

            }
            override fun onFailure(call: Call<AwrListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("2")
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMwriteList)
            }

        })

    }

    /**
     * 사용자가 입력한 데이터를 변수에 넣어주는 함수
     *  *///달력
    private fun getDateTimeCalendar(){
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
        Log.v("you1","${year},${month},${day}, 분 확인!!!!!!${hour},${minute}")

    }
    //달력@@@@@@@@@@@@
    private fun pickDate() {
        binding.editTextTime3.setOnClickListener {
            cIndex = 1
            getDateTimeCalendar()

            Log.v("you1","${year},${month},${day}")
            context?.let { it1 -> DatePickerDialog(it1,this, year, month, day).show() }
        }
        binding.editTextTime4.setOnClickListener {
            cIndex = 2
            getDateTimeCalendar()

            Log.v("you2","${year},${month},${day}")
            context?.let { it1 -> DatePickerDialog(it1,this, year, month, day).show() }

        }


    }
    //달력
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        TimePickerDialog(context,this,hour,minute,true).show()
        getDateTimeCalendar()
    }

    //달력
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, Minute: Int) {

        if (cIndex == 1){
            savedHour = hourOfDay
            savedMinute = Minute

            Log.v("@@","${hour},@@${minute}")
            binding.editTextTime3.text = "$savedYear-$savedMonth-$savedDay $savedHour 시"
            Write2SaveData.savestartTime = "$savedYear-$savedMonth-$savedDay $savedHour"

        } else if(cIndex == 2){
            savedHour = hourOfDay
            savedMinute = Minute

            Log.v("@@","${hour},@@${minute}")
            binding.editTextTime4.text = "$savedYear-$savedMonth-$savedDay $savedHour 시"
            Write2SaveData.saveendTime = "$savedYear-$savedMonth-$savedDay $savedHour"
        }



    }


}