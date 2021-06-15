package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.WeatherDTO

interface MainRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>,
    )
}