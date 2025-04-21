package com.example.habittrack.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
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

class CreateHabit: Fragment(R.layout.fragment_create_habit),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentCreateHabitBinding? = null
    private val binding get() = _binding!!

    private lateinit var habitViewModel: HabitViewModel

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCreateHabitBinding.bind(view)
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
        title = binding.etHabitTitle.text.toString().trim()
        description = binding.etHabitDescription.text.toString().trim()
        timeStamp = "$cleanDate $cleanTime"

        if (title.isNotEmpty() && description.isNotEmpty() && timeStamp.isNotEmpty() && drawableSelected != 0) {
            val habit = Habit(0, title, description, timeStamp, drawableSelected)
            habitViewModel.insertHabit(habit)
            Toast.makeText(context, "Habit created successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_createHabit_to_habitList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawableSelected() {
        binding.ivFastFoodSelected.setOnClickListener {
            binding.ivFastFoodSelected.isSelected = true
            binding.ivSmokingSelected.isSelected = false
            binding.ivTeaSelected.isSelected = false
            drawableSelected = R.drawable.baseline_fastfood_24
        }

        binding.ivSmokingSelected.setOnClickListener {
            binding.ivFastFoodSelected.isSelected = false
            binding.ivSmokingSelected.isSelected = true
            binding.ivTeaSelected.isSelected = false
            drawableSelected = R.drawable.baseline_smoke_free_24
        }

        binding.ivTeaSelected.setOnClickListener {
            binding.ivFastFoodSelected.isSelected = false
            binding.ivSmokingSelected.isSelected = false
            binding.ivTeaSelected.isSelected = true
            drawableSelected = R.drawable.baseline_no_drinks_24
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minuteOfDay: Int) {
        Log.d("CreateHabitFragment", "Time: $hourOfDay:$minuteOfDay")
        cleanTime = Calculations.cleanTime(hourOfDay, minuteOfDay)
        binding.tvTimeSelected.text = "Time: $cleanTime"
    }

    override fun onDateSet(view: DatePicker?, yearPicked: Int, monthPicked: Int, dayPicked: Int) {
        cleanDate = Calculations.cleanDate(dayPicked, monthPicked, yearPicked)
        binding.tvDateSelected.text = "Date: $cleanDate"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
