package com.example.myapplicationviewmodel.data

import kotlin.random.Random

class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = Random.nextInt(15, 35),
    val feelsLike: Int = Random.nextInt(temperature - 5, temperature + 5), //ощущаемая температура
    val humidity: Double = Random.nextDouble(0.0, 100.0), //влажность
    val probabilityOfPrecipitation: Int = Random.nextInt(0, 100), //вероятность осадков
    val windSpeed: Int = Random.nextInt(0, 50) //скорость ветра
)

fun getDefaultCity() =
    when (Random.nextInt(0, 10)) {
        0 -> City("Москва", 55.756, 37.617)
        1 -> City("Санкт-Петербург", 55.756, 37.617)
        2 -> City("Омск", 55.756, 37.617)
        3 -> City("Томск", 55.756, 37.617)
        4 -> City("Павлодар", 55.756, 37.617)
        5 -> City("Рязань", 55.756, 37.617)
        6 -> City("Барнаул", 55.756, 37.617)
        7 -> City("Новосибирск", 55.756, 37.617)
        8 -> City("Владивосток", 55.756, 37.617)
        9 -> City("Благовещенск", 55.756, 37.617)
        else -> City("Сочи", 55.756, 37.617)
    }



