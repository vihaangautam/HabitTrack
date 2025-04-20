package com.example.habittrack.ui.fragments.habitlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.data.models.Habit
import com.example.habittrack.databinding.RecyclerHabitItemBinding
import com.example.habittrack.logic.utils.Calculations
import com.example.habittrack.ui.fragments.habitlist.HabitListDirections

class HabitListAdapter : RecyclerView.Adapter<HabitListAdapter.MyViewHolder>() {

    private var habitList: List<Habit> = emptyList()

    inner class MyViewHolder(val binding: RecyclerHabitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvCardView.setOnClickListener {
                val position = adapterPosition
                val action: NavDirections =
                    HabitListDirections.actionHabitListToUpdateHabit(habitList[position])
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerHabitItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentHabit = habitList[position]
        with(holder.binding) {
            ivHabitIcon.setImageResource(currentHabit.imageId)
            tvItemTitle.text = currentHabit.habit_title
            tvItemDescription.text = currentHabit.habit_description
            tvTimeElapsed.text =
                Calculations.calculateTimeBetweenDates(currentHabit.habit_startTime)
            tvItemCreatedTimeStamp.text = "Since: ${currentHabit.habit_startTime}"
        }
    }

    override fun getItemCount(): Int = habitList.size

    fun setData(habit: List<Habit>) {
        this.habitList = habit
        notifyDataSetChanged()
    }
}
