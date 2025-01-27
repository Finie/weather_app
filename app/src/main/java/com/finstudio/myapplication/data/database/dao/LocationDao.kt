package com.finstudio.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.finstudio.myapplication.data.database.base.BaseDao
import com.finstudio.myapplication.data.database.entity.LocationData
import kotlinx.coroutines.flow.Flow
import java.util.UUID


@Dao
interface LocationDao: BaseDao<LocationData> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationData: LocationData)

    @Update
    suspend fun updateLocation(locationData: LocationData)

    @Delete
    suspend fun deleteLocation(locationData: LocationData)

    @Query("SELECT * FROM location ORDER BY id ASC")
    fun getAllLocations(): Flow<List<LocationData>>


    @Query("SELECT * FROM location WHERE active = 1")
    fun getCurrentLocation(): Flow<LocationData?>


}