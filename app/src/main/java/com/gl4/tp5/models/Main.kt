package com.gl4.tp5.models

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    var temp: Double,
    val temp_max: Double,
    val temp_min: Double
)