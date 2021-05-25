package com.example.myapplicationviewmodel.appState

import com.example.myapplicationviewmodel.data.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
