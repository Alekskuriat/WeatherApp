package com.example.myapplicationviewmodel.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.myapplicationviewmodel.repository.Repository
import com.example.myapplicationviewmodel.repository.RepositoryImpl
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.save.SaveLoad
import com.example.myapplicationviewmodel.save.SaveLoadImpl
import java.lang.Thread.sleep
import java.util.*
import kotlin.random.Random

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()

) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeather() = getDataFromLocalSource(true)

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)


    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian)
                        repositoryImpl.getWeatherFromLocalStorageRus()
                    else repositoryImpl.getWeatherFromLocalStorageWorld()
                )
            )
        }.start()
    }


}