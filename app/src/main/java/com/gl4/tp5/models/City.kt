package com.gl4.tp5.models

data class City(
    val coord: CoordX,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)