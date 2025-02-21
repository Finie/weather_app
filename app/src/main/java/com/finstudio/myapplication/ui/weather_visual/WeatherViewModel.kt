package com.finstudio.myapplication.ui.weather_visual

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finstudio.myapplication.data.database.entity.LocationData
import com.finstudio.myapplication.data.database.entity.WeatherData
import com.finstudio.myapplication.data.repository.LocationRepository
import com.finstudio.myapplication.data.repository.WeatherApiRepository
import com.finstudio.myapplication.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository,
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {


    val activeLocation: Flow<LocationData?> = locationRepository.activeLocation


    val activeWeather: Flow<WeatherData?> = weatherRepository.activeWeather

    val forecastData: Flow<List<WeatherData>> = weatherRepository.forecastData


    fun fetchWeatherData(longitude: Double, latitude: Double, units: String) {
        viewModelScope.launch {
            weatherApiRepository.getCurrentLocationWeatherData(
                latitude = latitude, longitude = longitude, units = units
            ).collect { weatherResponse ->
                if (weatherResponse.isSuccessful) {

                    val result = weatherResponse.body()
                    val locationId: UUID = UUID.randomUUID()

                    // save current weather forecast for offline support
                    Log.i("Creating", "Creating location and weather data")

                    saveLocationData(
                        LocationData(
                            id = locationId,
                            longitude = longitude,
                            latitude = latitude,
                            name = result?.name ?: "Current Location",
                            isFavourite = true,
                            active = true
                        ),
                        WeatherData(
                            id = UUID.randomUUID(),
                            fellsLike = result?.main?.feels_like ?: 0.0,
                            minTemp = result?.main?.temp_min ?: 0.0,
                            maxTemp = result?.main?.temp_max ?: 0.0,
                            weather = result?.weather?.get(0)?.main ?: "Unavailable",
                            condition = result?.weather?.get(0)?.main ?: "Unavailable",
                            locationId = locationId,
                            isActive = true,
                            date = getCurrentTimeFormatted()
                        )
                    )


                    fetchWeatherForecastData(
                        longitude = longitude, latitude = latitude, locationId = locationId
                    )
                } else {
                    // response not successful return error and offline stored data if any
                    Log.e(
                        "fetchWeatherForecastData: ",
                        "Error occurred: code  ${weatherResponse.code()}, ${weatherResponse.errorBody()}"
                    )
                }

            }
        }

    }

    fun fetchWeatherForecastData(longitude: Double, latitude: Double, locationId: UUID) {
        viewModelScope.launch {
            weatherApiRepository.getFiveDayWeatherForecast(
                latitude = latitude, longitude = longitude, units = "metric"
            ).collect { forecastResponse ->
                // save 5days forecast for offline support
                if (forecastResponse.isSuccessful) {
                    for (response in forecastResponse.body()?.list!!) {

                        saveWeatherData(
                            WeatherData(
                                id = UUID.randomUUID(),
                                fellsLike = response.main?.feels_like ?: 0.0,
                                minTemp = response.main?.temp_min ?: 0.0,
                                maxTemp = response.main?.temp_max ?: 0.0,
                                weather = response.weather?.get(0)?.main ?: "Unavailable",
                                condition = response.weather?.get(0)?.main ?: "Unavailable",
                                locationId = locationId,
                                isActive = false,
                                date = response.dt_txt ?: ""
                            )
                        )
                    }

                } else {
                    // response not successful return error and offline stored data if any
                    Log.e(
                        "fetchWeatherForecastData: ",
                        "Error occurred: code  ${forecastResponse.code()}, ${forecastResponse.errorBody()}"
                    )
                }
            }
        }
    }


    private fun saveLocationData(locationData: LocationData, weather: WeatherData?) {
        viewModelScope.launch {

            val location = locationRepository.activeLocation.first()

            if (location != null) {
                Log.e("Updating Location", "Updating Location data")
                locationData.id = location.id

                if (weather != null){
                    weather.locationId = location.id
                    saveActiveWeatherData(weather)
                }

                locationRepository.insertLocation(locationData)

            } else {
                Log.e("Creating Location", "Creating Location data")
                locationRepository.insertLocation(locationData)

                if (weather != null){
                    saveActiveWeatherData(weather)
                }
            }


        }

    }

    private fun saveActiveWeatherData(weatherData: WeatherData) {
        viewModelScope.launch {

            var weather = activeWeather.first()

            if (weather != null) {
                weatherData.id = weather.id
                weatherRepository.insertWeather(weatherData)
            } else {

                weatherRepository.insertWeather(weatherData)
            }

        }
    }

    private fun saveWeatherData(weatherData: WeatherData) {

        viewModelScope.launch {

            val weatherList = forecastData.first()

            if (!weatherList.isEmpty()) {
                for (weather in weatherList) {
                    if (weatherData.date == weather.date) {

                        weatherData.id = weather.id
                        weatherData.locationId = weather.locationId
                        weatherRepository.insertWeather(weatherData)
                    }

                }
            } else {

                weatherRepository.insertWeather(weatherData)
            }
        }
    }


    fun removeLocationData(locationData: LocationData) {
        viewModelScope.launch {


            locationRepository.deleteLocation(locationData)
        }
    }


    fun getCurrentTimeFormatted(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:00:00", Locale.getDefault())
        return formatter.format(Date())
    }


}