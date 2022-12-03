package com.gl4.tp5.services

import com.gl4.tp5.models.ForecastWeatherResponse
import com.gl4.tp5.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather?APPID=17db59488cadcad345211c36304a9266")
    fun getWeather(@Query("q") query: String) : Call<WeatherResponse>

    @GET("forecast/daily?APPID=17db59488cadcad345211c36304a9266")
    fun getForecast(@Query("q") query: String) : Call<ForecastWeatherResponse>
}