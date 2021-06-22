package com.example.myapplicationviewmodel.loader


import com.example.myapplicationviewmodel.BuildConfig
import com.example.myapplicationviewmodel.DTO.WeatherApi
import com.example.myapplicationviewmodel.WeatherDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REQUEST_API_KEY = "X-Yandex-API-Key"



class RemoteDataSource {

    private val weatherApi = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(WeatherApi::class.java)

    fun getWeatherDetails(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        weatherApi.getWeatherNow(lat.toString(), lon.toString())?.enqueue(callback)
    }


}