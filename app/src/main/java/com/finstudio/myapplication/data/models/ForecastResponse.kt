package com.finstudio.myapplication.data.models



data class ForecastResponse(
    var cod: String? = null,
    var message: Int = 0,
    var cnt: Int = 0,
    var list: ArrayList<ResponseList>? = null,
    var city: City? = null
) {
    override fun toString(): String {
        return "ForecastResponse(cod=$cod, message=$message, cnt=$cnt, list=$list, city=$city)"
    }
}

