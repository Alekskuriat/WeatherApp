package com.example.myapplicationviewmodel.DTO

import com.google.gson.annotations.SerializedName

class FactDTO(
    @SerializedName("temp")
    var temp: Float?,

    @SerializedName("feels_like")
    var feelsLike: Float?,

    @SerializedName("humidity")
    var humidity: Float?,

    @SerializedName("wind_speed")
    var windSpeed: Float?,

    @SerializedName("pressure_mm")
    var pressure: Float?,

    @SerializedName("condition")
    var condition: String?,

    @SerializedName("icon")
    var icon: String?
)