package com.gl4.tp5.models

data class ForecastWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Double
)