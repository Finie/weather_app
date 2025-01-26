package com.finstudio.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.finstudio.myapplication.data.database.dao.WeatherDao
import com.finstudio.myapplication.data.models.WeatherResponse


@Database(entities = [WeatherResponse::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}