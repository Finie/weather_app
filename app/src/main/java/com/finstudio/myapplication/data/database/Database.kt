package com.finstudio.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.finstudio.myapplication.data.database.dao.LocationDao
import com.finstudio.myapplication.data.database.dao.WeatherDao
import com.finstudio.myapplication.data.database.entity.LocationData
import com.finstudio.myapplication.data.database.entity.WeatherData
import com.finstudio.myapplication.data.models.WeatherResponse


@Database(entities = [WeatherData::class, LocationData::class], version = 2, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun locationDao(): LocationDao
}