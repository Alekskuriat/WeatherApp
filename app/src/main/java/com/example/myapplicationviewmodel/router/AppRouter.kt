package com.example.myapplicationviewmodel.router


import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.myapplicationviewmodel.R
import com.example.myapplicationviewmodel.ui.main.*


class AppRouter(
    private val fragmentManager: FragmentManager
){


    fun showStart() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }

    fun showCityWeather(bundle : Bundle){
        fragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance(bundle))
            .commit()
    }

    fun showAbout() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, AboutFragment())
            .addToBackStack("About")
            .commit()
    }

    fun showSettings() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, SettingFragment())
            .addToBackStack("Settings")
            .commit()
    }

    fun showCitySelect() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, CitySelectFragment())
            .commit()
    }

    fun showHistory(){
        fragmentManager.beginTransaction()
            .replace(R.id.container, HistoryFragment.newInstance())
            .addToBackStack("History")
            .commitAllowingStateLoss()
    }



}