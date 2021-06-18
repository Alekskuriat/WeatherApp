package com.example.myapplicationviewmodel.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Float,
    val feelsLike: Float,
    val humidity: Float,
    val windSpeed: Float,
    val pressure: Float,
    val condition: String
)

