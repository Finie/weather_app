package com.finstudio.myapplication.data.models

data class City(
    var id: Int = 0,
    var name: String? = null,
    var coord: Coord? = null,
    var country: String? = null,
    var population: Int = 0,
    var timezone: Int = 0,
    var sunrise: Int = 0,
    var sunset: Int = 0
)
