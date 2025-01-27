package com.finstudio.myapplication.data.repository

import com.finstudio.myapplication.data.database.dao.WeatherDao
import com.finstudio.myapplication.data.database.entity.WeatherData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherDao: WeatherDao) {

    val forecastData: Flow<List<WeatherData>> = weatherDao.getWeatherRecords()
    val activeWeather: Flow<WeatherData?> = weatherDao.getCurrentWeather()


    suspend fun insertWeather(weatherData: WeatherData) {
        weatherDao.insertWeather(weatherData)
    }


}