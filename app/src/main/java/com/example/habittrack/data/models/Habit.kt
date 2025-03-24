package com.example.habittrack.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize  // Updated import

@Parcelize
@Entity(tableName = "habit_table")
data class Habit(  // Use `data class` instead of `class`
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // Default value to avoid uninitialized property errors
    val habit_title: String,
    val habit_description: String,
    val habit_startTime: String,
    val imageId: Int
) : Parcelable  // Correct Parcelable implementation
