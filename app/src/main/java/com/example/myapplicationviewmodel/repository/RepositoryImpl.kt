package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.data.getRussianCities
import com.example.myapplicationviewmodel.data.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

}