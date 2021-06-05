package com.example.myapplicationviewmodel.DTO

import com.google.gson.annotations.SerializedName

class FactDTO {
    @SerializedName("temp")
    var temp = 0f

    @SerializedName("feels_like")
    var feelsLike = 0f

    @SerializedName("humidity")
    var humidity = 0f

    @SerializedName("wind_speed")
    var windSpeed = 0f

    @SerializedName("pressure_mm")
    var pressure = 0f
}