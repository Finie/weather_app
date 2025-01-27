package com.finstudio.myapplication.data.repository

import com.finstudio.myapplication.data.database.dao.LocationDao
import com.finstudio.myapplication.data.database.entity.LocationData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val locationDao: LocationDao) {

    val locations: Flow<List<LocationData>> = locationDao.getAllLocations()
    val activeLocation: Flow<LocationData?> = locationDao.getCurrentLocation()


    suspend fun insertLocation(locationData: LocationData) {
        locationDao.insertLocation(locationData)
    }


    suspend fun deleteLocation(locationData: LocationData) {
        locationDao.deleteLocation(locationData)
    }
}