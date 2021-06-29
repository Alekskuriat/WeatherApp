package com.example.myapplicationviewmodel.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationviewmodel.app.App.Companion.getHistoryDao
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.repository.LocalRepository
import com.example.myapplicationviewmodel.repository.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao().historyDao()),
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        Thread {
            historyLiveData.postValue(
                AppState.Success(historyRepository.getAllHistory())
            )
        }.start()

    }

    fun deleteEntityFromDB() {
        Thread {
            historyRepository.deleteAllEntity(historyRepository.getAllHistory())
            historyLiveData.postValue(
                AppState.Success(historyRepository.getAllHistory())
            )
        }.start()
    }
}