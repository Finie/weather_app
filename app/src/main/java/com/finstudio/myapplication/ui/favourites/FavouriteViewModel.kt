package com.finstudio.myapplication.ui.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finstudio.myapplication.data.database.entity.LocationData
import com.finstudio.myapplication.data.database.entity.WeatherData
import com.finstudio.myapplication.data.repository.LocationRepository
import com.finstudio.myapplication.data.repository.WeatherApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(private val locationRepository: LocationRepository, private val weatherApiRepository: WeatherApiRepository): ViewModel() {

    val locationList: Flow<List<LocationData>> = locationRepository.locations

    // MutableStateFlow to hold the list of WeatherData
    private val _savedWeatherData = MutableStateFlow<List<WeatherData>>(emptyList())
    val savedWeatherData: StateFlow<List<WeatherData>> = _savedWeatherData

    // MutableStateFlow to hold the active WeatherData
    private val _activeWeather = MutableStateFlow<WeatherData?>(null)
    val activeWeather: StateFlow<WeatherData?> = _activeWeather



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
                    updateActiveWeather(
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

                        addWeatherData(
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

    // Function to add weather data to the list
    private fun addWeatherData(weatherData: WeatherData) {
        _savedWeatherData.update { currentList ->
            currentList + weatherData
        }
    }



    private fun updateActiveWeather(weatherData: WeatherData) {
        _activeWeather.value = weatherData
    }


    fun getCurrentTimeFormatted(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:00:00", Locale.getDefault())
        return formatter.format(Date())
    }


}


