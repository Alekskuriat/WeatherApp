package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.data.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): Weather
}
