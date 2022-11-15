package com.lx.project5

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.lx.api.BasicClient
import com.lx.data.AcrListResponse
import com.lx.project5.databinding.FragmentWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class WriteFragment : Fragment(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    var _binding: FragmentWriteBinding? = null
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
        _binding = FragmentWriteBinding.inflate(inflater, container, false)

        writeShow()

        pickDate() //달력

        binding.editButton2.setOnClickListener {
            writeSave()
            acrAdd()

        }

        binding.selectDogButton1.setOnClickListener {
            writeSave()
            AppData.dogListIndex = 1
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMwriteSelect)

        }

        return binding.root


    }
    fun writeShow() {
        binding.dogNameShow.text = Write2SaveData.savedogName
        binding.editTextTime1.text = "${WriteSaveData.savestartTime} 시"
        binding.editTextTime2.text = "${WriteSaveData.saveendTime} 시"
        binding.editTextTextPersonName.setText(WriteSaveData.saveassignTitle.toString())
        binding.content.setText(WriteSaveData.saveassignContent.toString())
    }
    fun writeSave(){
        WriteSaveData.savedogName = binding.dogNameShow.text.toString()
        WriteSaveData.saveassignTitle = binding.editTextTextPersonName.text.toString()
        WriteSaveData.saveassignContent = binding.content.text.toString()
    }

    fun acrAdd() {


        BasicClient.api.acrAdd(
            requestCode = "1001",
            memberNo = AppData.loginData?.memberNo.toString(),
            careNo = WriteSaveData.savecareNo.toString(),
            dogNo = WriteSaveData.savedogNo.toString(),
            startTime = WriteSaveData.savestartTime.toString(),
            endTime = WriteSaveData.savestartTime.toString(),
            assignTitle = binding.editTextTextPersonName.text.toString(),
            assignContent = binding.content.text.toString()

        ).enqueue(object : Callback<AcrListResponse> {
            override fun onResponse(call: Call<AcrListResponse>, response: Response<AcrListResponse>) {
                (activity as MainActivity).showToast("1")


            }
            override fun onFailure(call: Call<AcrListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("2")
            }

        })

    }
    fun acrTodoAdd(acrn: Int){

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


    }
    //달력@@@@@@@@@@@@
    private fun pickDate() {
        binding.editTextTime1.setOnClickListener {
            cIndex = 1
            getDateTimeCalendar()


            context?.let { it1 -> DatePickerDialog(it1,this, year, month, day).show() }
        }
        binding.editTextTime2.setOnClickListener {
            cIndex = 2
            getDateTimeCalendar()


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


            binding.editTextTime1.text = "$savedYear-$savedMonth-$savedDay $savedHour 시"
            WriteSaveData.savestartTime = "$savedYear-$savedMonth-$savedDay $savedHour"

        } else if(cIndex == 2){
            savedHour = hourOfDay
            savedMinute = Minute


            binding.editTextTime2.text = "$savedYear-$savedMonth-$savedDay $savedHour 시"
            WriteSaveData.saveendTime = "$savedYear-$savedMonth-$savedDay $savedHour"
        }



    }

}