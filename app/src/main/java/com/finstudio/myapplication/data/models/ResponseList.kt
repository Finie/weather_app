package com.finstudio.myapplication.data.models


data class ResponseList(
    var dt: Int = 0,
    var main: Main? = null,
    var weather: ArrayList<Weather>? = null,
    var clouds: Clouds? = null,
    var wind: Wind? = null,
    var visibility: Int = 0,
    var pop: Double = 0.0,
    var rain: Rain? = null,
    var sys: Sys? = null,
    var dt_txt: String? = null
){
    override fun toString(): String {
        return "ResponseList(dt=$dt, main=$main, weather=$weather, clouds=$clouds, wind=$wind, visibility=$visibility, pop=$pop, rain=$rain, sys=$sys, dt_txt=$dt_txt)"
    }
}
