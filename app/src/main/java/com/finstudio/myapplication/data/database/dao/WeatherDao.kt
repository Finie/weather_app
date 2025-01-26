package com.finstudio.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finstudio.myapplication.data.models.WeatherResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(weatherResponse: WeatherResponse)

    @Delete
    suspend fun deleteUser(weatherResponse: WeatherResponse)

    @Query("SELECT * FROM weather ORDER BY id ASC")
    fun getAllUsers(): Flow<List<WeatherResponse>>


}