package com.example.myapplicationviewmodel.utils

import com.example.myapplicationviewmodel.DTO.FactDTO
import com.example.myapplicationviewmodel.WeatherDTO
import com.example.myapplicationviewmodel.data.City
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.data.getDefaultCity
import com.example.myapplicationviewmodel.room.HistoryEntity


fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.factDTO!!
    return listOf(Weather(getDefaultCity(),
        fact.temp!!,
        fact.feelsLike!!,
        fact.humidity!!,
        fact.windSpeed!!,
        fact.pressure!!,
        fact.condition!!,
        fact.icon))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0),
            it.temperature,
            it.feelsLike,
            it.humidity,
            it.windSpeed,
            it.pressure,
            it.condition,
            id = it.id
        )
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(
        0,
        weather.city.city,
        weather.temperature,
        weather.feelsLike,
        weather.humidity,
        weather.windSpeed,
        weather.pressure,
        weather.condition)
}
