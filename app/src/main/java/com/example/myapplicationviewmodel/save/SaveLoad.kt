package com.example.myapplicationviewmodel.save

import android.content.Context
import com.example.myapplicationviewmodel.data.Weather

interface SaveLoad {
    fun save(context: Context?, weather: Weather?)
    fun load(context: Context?) : Weather
}