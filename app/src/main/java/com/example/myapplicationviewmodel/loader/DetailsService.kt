package com.example.myapplicationviewmodel.loader

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplicationviewmodel.WeatherDTO
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myapplicationviewmodel.DTO.WeatherApi
import com.example.myapplicationviewmodel.ui.main.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val LATITUDE_EXTRA = "Latitude"
const val LONGITUDE_EXTRA = "Longitude"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
private const val REQUEST_API_KEY = "X-Yandex-API-Key"



class DetailsService (name: String = "MainFragment") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val lat = intent.getDoubleExtra(LATITUDE_EXTRA, 0.0)
            val lon = intent.getDoubleExtra(LONGITUDE_EXTRA, 0.0)
            if (lat == 0.0 && lon == 0.0) {
                onEmptyData()
            } else {
                loadWeather(lat.toString(), lon.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadWeather(lat: String, lon: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weather.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(WeatherApi::class.java)
        val call = api.getWeatherNow(lat, lon)

        call?.enqueue(object : Callback<WeatherDTO> {
            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                onErrorRequest("Error")
            }

            override fun onResponse(
                call: Call<WeatherDTO>,
                response: Response<WeatherDTO>,
            ) {
                if (response.isSuccessful){
                //if (response.code() == 200) {
                    onResponse(response)
                }
            }
        })
    }


    private fun onResponse(weatherDTO: Response<WeatherDTO>) {
        val fact = weatherDTO.body()?.factDTO
        if (fact == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(fact.temp, fact.feelsLike, fact.humidity, fact.windSpeed, fact.pressure)
        }
    }

    private fun onSuccessResponse(
        temp: Float?, feelsLike: Float?, humidity: Float?, windSpeed: Float?, pressure: Float?
    ) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TEMP_EXTRA, temp)
        broadcastIntent.putExtra(DETAILS_FEELS_LIKE_EXTRA, feelsLike)
        broadcastIntent.putExtra(DETAILS_HUMIDITY_EXTRA, humidity)
        broadcastIntent.putExtra(DETAILS_WINDSPEED_EXTRA, windSpeed)
        broadcastIntent.putExtra(DETAILS_PRESSURE_EXTRA, pressure)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)

    }
}