package com.finstudio.myapplication.data.database.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(items: T)

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)

}