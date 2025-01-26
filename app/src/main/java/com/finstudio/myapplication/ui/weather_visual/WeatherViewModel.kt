package com.finstudio.myapplication.ui.weather_visual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finstudio.myapplication.data.models.WeatherResponse
import com.finstudio.myapplication.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    private val _weatherResponse = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherResponse;

    fun fetchWeatherData(longitude: Double, latitude: Double, units: String){

        viewModelScope.launch {
            weatherRepository.getCurrentLocationWeatherData( latitude = latitude, longitude = longitude, units = units)
                .collect{ weatherResponse ->
                    _weatherResponse.value = weatherResponse.body()
                }
        }


    }




}