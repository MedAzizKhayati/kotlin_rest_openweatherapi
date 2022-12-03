package com.gl4.tp5.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gl4.tp5.models.ForecastWeatherResponse
import com.gl4.tp5.models.WeatherResponse
import com.gl4.tp5.services.RetrofitHelper

class WeatherModel : ViewModel() {
    val weatherResponse: MutableLiveData<WeatherResponse> by lazy {
        MutableLiveData<WeatherResponse>(null)
    }

    val forecastResponse: MutableLiveData<ForecastWeatherResponse> by lazy {
        MutableLiveData<ForecastWeatherResponse>(null)
    }

    init {
        Log.d("WeatherModel", "init")
        getWeatherResponse("Tunis")
        getForecastResponse("Tunis")
    }

    fun getWeatherResponse(query: String) {
        Log.d("WeatherModel", "getWeatherResponse")
        RetrofitHelper.retrofitService.getWeather(query).enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(call: retrofit2.Call<WeatherResponse>, response: retrofit2.Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weatherResponse.value = response.body()
                    Log.d("WeatherModel", "onResponse")
                }
            }

            override fun onFailure(call: retrofit2.Call<WeatherResponse>, t: Throwable) {
                Log.e("WeatherModel", "Error while fetching weather data")
                weatherResponse.value = null
            }
        })
    }

    fun getForecastResponse(query: String){
        Log.d("WeatherModel", "getForecastResponse")
        RetrofitHelper.retrofitService.getForecast(query).enqueue(object : retrofit2.Callback<ForecastWeatherResponse> {
            override fun onResponse(call: retrofit2.Call<ForecastWeatherResponse>, response: retrofit2.Response<ForecastWeatherResponse>) {
                if (response.isSuccessful) {
                    forecastResponse.value = response.body()
                    Log.d("WeatherModel", "onResponse")
                }
            }

            override fun onFailure(call: retrofit2.Call<ForecastWeatherResponse>, t: Throwable) {
                Log.e("WeatherModel", "Error while fetching weather data")
                forecastResponse.value = null
            }
        })
    }
}