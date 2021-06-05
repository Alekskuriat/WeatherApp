package com.example.myapplicationviewmodel.loader

import com.example.myapplicationviewmodel.WeatherDTO
import retrofit2.Response

interface WeatherLoaderListener {
    fun onLoaded(response: Response<WeatherDTO>)
    fun onFailed(str: String)
}
