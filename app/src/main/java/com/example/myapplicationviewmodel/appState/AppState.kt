package com.example.myapplicationviewmodel.appState

import com.example.myapplicationviewmodel.contacts.Contact
import com.example.myapplicationviewmodel.data.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

sealed class AppStateContacts {
    data class Success(val listContacts: List<Contact>) : AppStateContacts()
    data class Error(val error: Throwable) : AppStateContacts()
    object Loading : AppStateContacts()
}
