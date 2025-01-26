package com.finstudio.myapplication.data.models

data class Weather(
    var id: Int = 0,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
) {
    override fun toString(): String {
        return """
            Weather(
                ID: $id,
                Condition: ${main ?: "N/A"},
                Description: ${description ?: "N/A"},
                Icon: ${icon ?: "N/A"}
            )
        """.trimIndent()
    }
}
