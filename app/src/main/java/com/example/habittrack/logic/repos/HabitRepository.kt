package com.example.habittrack.logic.repos

import androidx.lifecycle.LiveData
import com.example.habittrack.data.models.Habit
import com.example.habittrack.logic.dao.HabitDao

class HabitRepository(private val habitDao: HabitDao) {
    val getAllHabits: LiveData<List<Habit>> = habitDao.getAllHabits()
    suspend fun addHabit(habit: Habit) {
        habitDao.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    suspend fun deleteAllHabits() {
        habitDao.deleteAll()
    }
}