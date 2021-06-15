package com.example.myapplicationviewmodel.utils

import com.example.myapplicationviewmodel.DTO.FactDTO
import com.example.myapplicationviewmodel.WeatherDTO
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.data.getDefaultCity


fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.factDTO!!
    return listOf(Weather(getDefaultCity(),
        fact.temp!!,
        fact.feelsLike!!,
        fact.humidity!!,
        fact.windSpeed!!,
        fact.pressure!!,
        fact.condition!!))
}
