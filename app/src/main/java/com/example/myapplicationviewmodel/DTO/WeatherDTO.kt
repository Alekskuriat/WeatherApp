package com.example.myapplicationviewmodel

import com.example.myapplicationviewmodel.DTO.FactDTO
import com.google.gson.annotations.SerializedName

class WeatherDTO (
    @SerializedName("fact")
    var factDTO: FactDTO?
)
