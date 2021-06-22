package com.example.myapplicationviewmodel.repository

import com.example.myapplicationviewmodel.app.App
import com.example.myapplicationviewmodel.data.Weather
import com.example.myapplicationviewmodel.room.HistoryDao
import com.example.myapplicationviewmodel.utils.convertHistoryEntityToWeather
import com.example.myapplicationviewmodel.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }

    override fun deleteAllEntity(listWeather: List<Weather>) {
        val listId = mutableListOf<Long>()
        listWeather.forEach {
            listId.add(it.id)
        }
        localDataSource.deleteByIdList(listId)
    }


}