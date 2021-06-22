package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.WeatherDTO
import com.example.myapplicationviewmodel.loader.RemoteDataSource


class MainRepositoryImpl (private val remoteDataSource: RemoteDataSource) :
    MainRepository {

    override fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    ) {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }


}