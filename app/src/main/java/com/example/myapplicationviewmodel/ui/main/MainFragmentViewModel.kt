package com.example.myapplicationviewmodel.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationviewmodel.WeatherDTO
import com.example.myapplicationviewmodel.app.App
import com.example.myapplicationviewmodel.app.App.Companion.getHistoryDao
import com.example.myapplicationviewmodel.appState.AppState
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.loader.RemoteDataSource
import com.example.myapplicationviewmodel.repository.LocalRepository
import com.example.myapplicationviewmodel.repository.LocalRepositoryImpl
import com.example.myapplicationviewmodel.repository.MainRepository
import com.example.myapplicationviewmodel.repository.MainRepositoryImpl
import com.example.myapplicationviewmodel.room.HistoryDao
import com.example.myapplicationviewmodel.utils.convertDtoToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MainFragmentViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: MainRepository = MainRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao().historyDao())


) : ViewModel() {

    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(lat, lon, callBack)
    }

    fun saveCityToDB(weather: Weather) {
        historyRepository.saveEntity(weather)

    }

    private val callBack = object :
        Callback<WeatherDTO> {

        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse: WeatherDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: WeatherDTO): AppState {
            val fact = serverResponse.factDTO
            return if (fact?.temp == null ||
                fact.feelsLike == null ||
                fact.humidity == null ||
                fact.pressure == null ||
                fact.windSpeed == null ||
                fact.condition.isNullOrEmpty()
            ) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(serverResponse))
            }
        }
    }
}