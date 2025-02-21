package com.finstudio.myapplication.di

import android.content.Context
import androidx.room.Room
import com.finstudio.myapplication.data.database.Database
import com.finstudio.myapplication.data.database.dao.LocationDao
import com.finstudio.myapplication.data.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {



    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database{
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "weather_store"
        ).fallbackToDestructiveMigration().build()

    }


    @Provides
    fun provideWeatherDao(database: Database): WeatherDao {
        return database.weatherDao()
    }


    @Provides
    fun provideLocationDao(database: Database): LocationDao {
        return  database.locationDao()
    }



}