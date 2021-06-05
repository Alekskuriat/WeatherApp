package com.example.myapplicationviewmodel.DTO


import com.example.myapplicationviewmodel.BuildConfig
import com.example.myapplicationviewmodel.WeatherDTO
import retrofit2.Call

import retrofit2.http.*

interface WeatherApi {

    @Headers(BuildConfig.API_KEY)
    @GET("v2/informers?")
    fun getWeatherNow(
        @Query("lat") lat:String?,
        @Query("lon") lon:String?
    ) : Call<WeatherDTO>?

}