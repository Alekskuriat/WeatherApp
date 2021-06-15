package com.example.myapplicationviewmodel.repository

import okhttp3.Callback


interface MainRepository {
    fun getWeatherDetailsFromServer(requestLink: String, callback: Callback)
}