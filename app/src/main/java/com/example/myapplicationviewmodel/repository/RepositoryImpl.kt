package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.data.getRussianCities
import com.example.myapplicationviewmodel.data.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorage() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

}