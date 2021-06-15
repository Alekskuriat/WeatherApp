package com.example.myapplicationviewmodel.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt
import kotlin.random.Random

@Parcelize
class Weather(
    val city: City = getDefaultCity(),
    val temperature: Float = 0f,
    val feelsLike: Float = 0f,
    val humidity: Float = 0f, //влажность
    val windSpeed: Float = 0f, //скорость ветра
    val pressure: Float = 0f,
    val condition: String = "sunny"
) : Parcelable

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

fun getWorldCities() = listOf(
        Weather(City("Лондон", 51.5085300, -0.1257400)),
        Weather(City("Токио", 35.6895000, 139.6917100)),
        Weather(City("Париж", 48.8534100, 2.3488000)),
        Weather(City("Берлин", 52.52000659999999, 13.404953999999975)),
        Weather(City("Рим", 41.9027835, 12.496365500000024)),
        Weather(City("Минск", 53.90453979999999, 27.561524400000053)),
        Weather(City("Стамбул", 41.0082376, 28.97835889999999)),
        Weather(City("Вашингтон", 38.9071923, -77.03687070000001)),
        Weather(City("Киев", 50.4501, 30.523400000000038)),
        Weather(City("Пекин", 39.90419989999999, 116.40739630000007))
    )


fun getRussianCities() = listOf(
        Weather(City("Москва", 55.755826, 37.617299900000035)),
        Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038)),
        Weather(City("Новосибирск", 55.00835259999999, 82.93573270000002)),
        Weather(City("Екатеринбург", 56.83892609999999, 60.60570250000001)),
        Weather(City("Нижний Новгород", 56.2965039, 43.936059)),
        Weather(City("Казань", 55.8304307, 49.06608060000008)),
        Weather(City("Челябинск", 55.1644419, 61.4368432)),
        Weather(City("Омск", 54.9884804, 73.32423610000001)),
        Weather(City("Ростов-на-Дону", 47.2357137, 39.701505)),
        Weather(City("Уфа", 54.7387621, 55.972055400000045)),
        Weather(City("Бийск", 52.3211, 85.1225))
    )






