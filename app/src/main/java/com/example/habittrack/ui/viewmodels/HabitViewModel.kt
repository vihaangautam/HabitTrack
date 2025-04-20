package com.example.habittrack.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.habittrack.data.database.HabitDatabase
import com.example.habittrack.data.models.Habit
import com.example.habittrack.logic.repos.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ✅ Remove the unnecessary outer class declaration
class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository
    val getAllHabits: LiveData<List<Habit>>

    init {
        val habitDao = HabitDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)
        getAllHabits = repository.getAllHabits
    }

    // ✅ Rename `addHabit` to `insertHabit` to match your fragment code
    fun insertHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(habit)
        }
    }

    fun deleteAllHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHabits()
        }
    }
}
