package com.example.myapplicationviewmodel.save

import android.content.Context
import com.example.myapplicationviewmodel.MainActivity
import com.example.myapplicationviewmodel.data.Weather
import com.google.gson.Gson

class SaveLoadImpl : SaveLoad {
    override fun save(context: Context?, weather: Weather?) {
        val activity: MainActivity = context as MainActivity
        val sharedPreferences = context.getSharedPreferences("SP", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(weather)
        editor.putString("weather", json)
        editor.apply()
    }

    override fun load(context: Context?) : Weather {
        val activity: MainActivity = context as MainActivity
        val sharedPreferences = context.getSharedPreferences("SP", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("weather", "")
        val weather: Weather = gson.fromJson(json, Weather::class.java)
        return weather
            }

}