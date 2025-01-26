package com.finstudio.myapplication.data.models


data class WeatherResponse(
    var coord: Coord? = null,
    var weather: ArrayList<Weather>? = null,
    var base: String? = null,
    var main: Main? = null,
    var visibility: Int = 0,
    var wind: Wind? = null,
    var clouds: Clouds? = null,
    var dt: Int = 0,
    var sys: Sys? = null,
    var timezone: Int = 0,
    var id: Int = 0,
    var name: String? = null,
    var cod: Int = 0
){
    override fun toString(): String {
        return """
            WeatherResponse(
                Location Name: $name,
                Coordinates: ${coord ?: "N/A"},
                Weather: ${weather ?: "N/A"},
                Temperature: ${main?.temp ?: "N/A"} °C,
                Wind Speed: ${wind?.speed ?: "N/A"} m/s,
                Visibility: $visibility meters,
                Clouds: ${clouds?.all ?: "N/A"}%
            )
        """.trimIndent()
    }
}
