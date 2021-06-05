package com.example.myapplicationviewmodel.loader

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplicationviewmodel.DTO.WeatherApi
import com.example.myapplicationviewmodel.WeatherDTO
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherLoader(
    private val listener: WeatherLoaderListener,
    private val lat: Double,
    private val lon: Double,
) {


    @RequiresApi(Build.VERSION_CODES.N)
    fun loadWeather() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weather.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(WeatherApi::class.java)
        val call = api.getWeatherNow(lat.toString(), lon.toString())

        call?.enqueue(object : Callback<WeatherDTO> {
            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                listener.onFailed("Ошибка загрузки")
            }

            override fun onResponse(
                call: Call<WeatherDTO>,
                response: Response<WeatherDTO>,
            ) {
                if (response.code() == 200) {
                    listener.onLoaded(response)
                }
            }
        })
    }
}
