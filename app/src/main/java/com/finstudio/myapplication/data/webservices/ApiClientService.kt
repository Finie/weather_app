package com.finstudio.myapplication.data.webservices

import com.finstudio.myapplication.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiClientService {

    @GET("/data/2.5/weather")
    suspend fun getCurrentLocationWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String // Include your API key if required
    ): Response<WeatherResponse>

//
//    @GET("/data/2.5/weather?lat={latitude}&lon={longitude}&units={units}")
//    suspend fun getCurrentLocationWeatherData(@Path("longitude") longitude: Double, @Path("latitude") latitude: Double, @Path("units") units: String): WeatherResponse
//

    @GET("/data/2.5/forecast?lat={latitude}&lon={longitude}&appid={appId}")
    suspend fun getFiveDaysForecast(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double, @Path("appId") appId: String)


}