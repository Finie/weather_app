package com.finstudio.myapplication.data.database.entity


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "location")
data class LocationData(
    @PrimaryKey
    var id: UUID,
    var longitude: Double,
    var latitude: Double,
    var name: String,
    var isFavourite: Boolean,
    var active: Boolean
) {
    override fun toString(): String {
        return "LocationData(id=$id, name=$name, latitude=$latitude, longitude=$longitude),  isFavourite=$isFavourite),  active=$active)"
    }
}
