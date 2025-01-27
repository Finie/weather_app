package com.finstudio.myapplication.data.repository

import android.util.Log
import com.finstudio.myapplication.data.models.ForecastResponse
import com.finstudio.myapplication.data.models.WeatherResponse
import com.finstudio.myapplication.data.webservices.ApiClientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class WeatherApiRepository  @Inject constructor(private val apiClientService: ApiClientService){

    private val apiKey: String = "Add weather api_key here"


    fun getCurrentLocationWeatherData(latitude: Double, longitude: Double, units: String): Flow<Response<WeatherResponse>> = flow {

        val weatherResponse = apiClientService.getCurrentLocationWeatherData(
            longitude = longitude, latitude = latitude, units = units,
            apiKey = apiKey
        )
        emit(weatherResponse)

    }.catch{ exception ->
        Log.e("LocationWeatherData: ", "error occurred: $exception", null)
    }




    fun getFiveDayWeatherForecast(latitude: Double, longitude: Double, units: String) : Flow<Response<ForecastResponse>> = flow<Response<ForecastResponse>> {

        val forecastData = apiClientService.getFiveDaysForecast(
            latitude = latitude,
            longitude = longitude,
            apiKey = apiKey,
            units =units
        )

        emit(forecastData)

    }.catch { exception ->
        Log.e("FiveDayWeatherForecast: ", "error occurred: $exception", null)
    }





}