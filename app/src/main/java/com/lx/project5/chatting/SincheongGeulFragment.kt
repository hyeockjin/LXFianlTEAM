package com.lx.project5.chatting

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lx.project5.MainActivity
import com.lx.project5.appdata.GeulSaveData
import com.lx.project5.databinding.FragmentSincheongGeulBinding
import java.util.*


class SincheongGeulFragment : Fragment(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var _binding: FragmentSincheongGeulBinding? = null
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
        _binding = FragmentSincheongGeulBinding.inflate(inflater, container, false)

        writeShow()
        pickDate()


        binding.sincheonghagi.setOnClickListener {
            toast("신청이 완료되었습니다.")
        }

        binding.sincheonggeulToChat.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEM1)
        }

        return binding.root
    }

    fun writeShow() {
        binding.myDog.text = GeulSaveData.saveDog
        binding.startTime.text = "${GeulSaveData.saveStartTime} 시"
        binding.endTime.text = "${GeulSaveData.saveEndTime} 시"
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
        binding.startTime.setOnClickListener {
            cIndex = 1
            getDateTimeCalendar()

            Log.v("you1","${year},${month},${day}")
            context?.let { it1 -> DatePickerDialog(it1,this, year, month, day).show() }
        }
        binding.endTime.setOnClickListener {
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
            binding.startTime.text = "$savedYear-$savedMonth-$savedDay $savedHour 시"
            GeulSaveData.saveStartTime = "$savedYear-$savedMonth-$savedDay $savedHour"

        } else if(cIndex == 2){
            savedHour = hourOfDay
            savedMinute = Minute

            Log.v("@@","${hour},@@${minute}")
            binding.endTime.text = "$savedYear-$savedMonth-$savedDay $savedHour 시"
            GeulSaveData.saveEndTime = "$savedYear-$savedMonth-$savedDay $savedHour"
        }



    }

    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }


}