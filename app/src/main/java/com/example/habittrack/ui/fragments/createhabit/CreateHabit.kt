package com.example.habittrack.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittrack.R
import com.example.habittrack.data.models.Habit
import com.example.habittrack.databinding.FragmentCreateHabitBinding
import com.example.habittrack.logic.utils.Calculations
import com.example.habittrack.ui.viewmodels.HabitViewModel
import java.util.*

class CreateHabit : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var habitViewModel: HabitViewModel

    private var drawableSelected = 0
    private var cleanDate = ""
    private var cleanTime = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitViewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        binding.btnConfirm.setOnClickListener {
            addHabitToDB()
        }

        pickDateAndTime()
        drawableSelected()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun pickDateAndTime() {
        binding.btnPickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnPickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
    }

    private fun addHabitToDB() {
        val title = binding.etHabitTitle.text.toString()
        val description = binding.etHabitDescription.text.toString()
        val timeStamp = "$cleanDate $cleanTime"

        if (title.isNotEmpty() && description.isNotEmpty() && drawableSelected != 0) {
            val habit = Habit(0, title, description, timeStamp, drawableSelected)
            habitViewModel.addHabit(habit)

            Toast.makeText(context, "Habit created successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_createHabit_to_habitListFragment)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawableSelected() {
        binding.ivFastFoodSelected.setOnClickListener {
            drawableSelected = R.drawable.baseline_fastfood_24
            selectDrawable(binding.ivFastFoodSelected)
        }

        binding.ivSmokingSelected.setOnClickListener {
            drawableSelected = R.drawable.baseline_smoke_free_24
            selectDrawable(binding.ivSmokingSelected)
        }

        binding.ivTeaSelected.setOnClickListener {
            drawableSelected = R.drawable.baseline_no_drinks_24
            selectDrawable(binding.ivTeaSelected)
        }
    }

    private fun selectDrawable(selectedView: View) {
        binding.ivFastFoodSelected.isSelected = selectedView == binding.ivFastFoodSelected
        binding.ivSmokingSelected.isSelected = selectedView == binding.ivSmokingSelected
        binding.ivTeaSelected.isSelected = selectedView == binding.ivTeaSelected
    }

    override fun onDateSet(view: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        binding.tvDateSelected.text = "Date: $cleanDate"
    }

    override fun onTimeSet(view: TimePicker?, hourX: Int, minuteX: Int) {
        cleanTime = Calculations.cleanTime(hourX, minuteX)
        binding.tvTimeSelected.text = "Time: $cleanTime"
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
