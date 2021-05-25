package com.example.myapplicationviewmodel.ui.main

import androidx.lifecycle.*
import com.example.myapplicationviewmodel.repository.Repository
import com.example.myapplicationviewmodel.repository.RepositoryImpl
import com.example.myapplicationviewmodel.appState.AppState
import java.lang.Thread.sleep
import java.util.*
import kotlin.random.Random

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()

) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeather() = getDataFromLocalSource()

    fun getWeatherFromLocalSource() = getDataFromLocalSource()


    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        val appStateRandom: Int = Random.nextInt(1, 3)
        Thread {
            sleep(1000)
            if (appStateRandom == 1)
                liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
            else
                liveDataToObserve.postValue(AppState.Error(Throwable()))
        }.start()

    }


}