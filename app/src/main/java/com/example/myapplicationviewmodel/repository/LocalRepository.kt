package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.data.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}