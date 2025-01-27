package com.finstudio.myapplication.ui.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finstudio.myapplication.data.database.entity.LocationData
import com.finstudio.myapplication.data.database.entity.WeatherData
import com.finstudio.myapplication.data.repository.LocationRepository
import com.finstudio.myapplication.utils.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(private val locationService: LocationService, private val locationRepository: LocationRepository): ViewModel() {


    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> get() = _location

    init {
        fetchLocation()
    }

    fun fetchLocation() {
        viewModelScope.launch {
            try {
                val currentLocation = locationService.getCurrentLocation()
                currentLocation.addOnSuccessListener { location ->
                    _location.value = location
                }.addOnFailureListener { exception ->
                    // Handle any errors
                    exception.printStackTrace()
                }
            } catch (e: SecurityException) {
                // Handle permission-related errors
                e.printStackTrace()
            }
        }
    }



    fun saveLocationData(locationData: LocationData) {

        viewModelScope.launch {
            Log.e("Creating Location", "Creating new favourite Location")
            locationRepository.insertLocation(locationData)
        }

    }


}