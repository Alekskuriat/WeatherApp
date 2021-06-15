package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.loader.RemoteDataSource
import okhttp3.Callback

class MainRepositoryImpl (private val remoteDataSource: RemoteDataSource) :
    MainRepository {

    override fun getWeatherDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getWeatherDetails(requestLink, callback)
    }

}