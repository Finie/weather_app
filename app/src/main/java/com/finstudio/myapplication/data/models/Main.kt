package com.finstudio.myapplication.data.models

data class Main(
    var temp: Double = 0.0,
    var feels_like: Double = 0.0,
    var temp_min: Double = 0.0,
    var temp_max: Double = 0.0,
    var pressure: Int = 0,
    var humidity: Int = 0,
    var sea_level: Int = 0,
    var grnd_level: Int = 0
){
    override fun toString(): String {
        return """
            Main(
                Temperature: $temp °C,
                Feels Like: $feels_like °C,
                Min Temperature: $temp_min °C,
                Max Temperature: $temp_max °C,
                Pressure: $pressure hPa,
                Humidity: $humidity%,
                Sea Level Pressure: $sea_level hPa,
                Ground Level Pressure: $grnd_level hPa
            )
        """.trimIndent()
    }
}
