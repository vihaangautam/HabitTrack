package com.example.habittrack.ui.fragments.createhabit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.R
import com.example.habittrack.ui.viewmodels.HabitViewModel
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 * Use the [CreateHabit.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateHabit : Fragment(R.layout.fragment_create_habit) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private lateinit var habitViewModel: HabitViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        //Add habit to database
        btn_confirm.setOnClickListener {
            addHabitToDB()
        }
        //Pick a date and time
        pickDateAndTime()

        //Selected and image to put into our list
        drawableSelected()
    }



    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {

        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "Date: $cleanDate"
    }
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }


    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }


}