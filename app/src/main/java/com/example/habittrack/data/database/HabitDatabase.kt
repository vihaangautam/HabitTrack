package com.example.habittrack.data.database

import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habittrack.data.models.Habit
import com.example.habittrack.logic.dao.HabitDao

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase(){
    abstract fun habitdao() :HabitDao

    companion object{
        @Volatile
        private var INSTANCE: HabitDatabase? = null




    }

}