package com.finstudio.myapplication.data.repository

import android.util.Log
import com.finstudio.myapplication.data.models.WeatherResponse
import com.finstudio.myapplication.data.webservices.ApiClientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository  @Inject constructor(private val apiClientService: ApiClientService){



    fun getCurrentLocationWeatherData(latitude: Double, longitude: Double, units: String): Flow<Response<WeatherResponse>> = flow {

        val weatherResponse = apiClientService.getCurrentLocationWeatherData(
            longitude = longitude, latitude = latitude, units = units,
            apiKey = "c14f401bded8cbb1929b156989f3906c"
        )

        Log.e("getCurrentLocationWeatherData","Response: ${weatherResponse.body().toString()}", null)

        emit(weatherResponse)

    }.catch{ exception ->
        Log.e("WeatherRepository: ", "error occurred: $exception", null);
    }






}