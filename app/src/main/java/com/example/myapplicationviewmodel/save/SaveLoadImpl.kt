package com.example.myapplicationviewmodel.save

import android.content.Context
import com.example.myapplicationviewmodel.MainActivity
import com.example.myapplicationviewmodel.data.Weather
import com.google.gson.Gson

class SaveLoadImpl : SaveLoad {
    override fun save(context: Context?, weather: Weather?) {
        context?.getSharedPreferences("SP", Context.MODE_PRIVATE)
            ?.edit()
            ?.putString("weather", Gson().toJson(weather))
            ?.apply()
    }

    override fun load(context: Context?): Weather {
        return Gson()
            .fromJson(
                context
                    ?.getSharedPreferences("SP", Context.MODE_PRIVATE)
                    ?.getString("weather", ""), Weather::class.java
            )
    }

}