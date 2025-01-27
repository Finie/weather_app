package com.finstudio.myapplication.data.webservices

import com.finstudio.myapplication.data.models.ForecastResponse
import com.finstudio.myapplication.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiClientService {

    @GET("/data/2.5/weather")
    suspend fun getCurrentLocationWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>


    @GET("/data/2.5/forecast")
    suspend fun getFiveDaysForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<ForecastResponse>


}