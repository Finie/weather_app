package com.finstudio.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finstudio.myapplication.data.database.base.BaseDao
import com.finstudio.myapplication.data.database.entity.WeatherData
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao: BaseDao<WeatherData> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherData: WeatherData)

    @Query("SELECT * FROM weather WHERE isActive= 0 ORDER BY date ASC")
    fun getWeatherRecords(): Flow<List<WeatherData>>

    @Query("SELECT * FROM weather WHERE isActive= 1 ")
    fun getCurrentWeather(): Flow<WeatherData?>

}