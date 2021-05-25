package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.data.Weather

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }
}