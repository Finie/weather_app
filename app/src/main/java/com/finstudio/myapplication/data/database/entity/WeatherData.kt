package com.finstudio.myapplication.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "weather")
data class WeatherData(
    @PrimaryKey
    var id:  UUID,
    var fellsLike: Double = 0.0,
    var minTemp: Double = 0.0,
    var maxTemp: Double = 0.0,
    var weather: String = "",
    var condition: String = "",
    var locationId: UUID? = null,
    var isActive: Boolean = false,
    var date: String = ""
){
    override fun toString(): String {
        return "WeatherData(id=$id, fellsLike=$fellsLike, minTemp=$minTemp, maxTemp=$maxTemp, weather='$weather', condition='$condition', locationId=$locationId), date=$date)"
    }
}
