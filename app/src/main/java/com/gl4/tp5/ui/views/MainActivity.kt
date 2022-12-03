package com.gl4.tp5.ui.views

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gl4.tp5.R
import com.gl4.tp5.databinding.ActivityMainBinding
import com.gl4.tp5.ui.adapters.ForecastAdapter
import com.gl4.tp5.ui.viewmodels.WeatherModel
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    // view binding
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // view model
        viewModel.weatherResponse.observe(this, Observer { weatherResponse ->
            if (weatherResponse != null) {
                binding.country.text = weatherResponse.name
                val tempInC = (weatherResponse.main.temp - 273.15).toInt()
                binding.temperature.text = "$tempInC C°"
                binding.details.text = weatherResponse.weather[0].description
                binding.humidity.text = "Humidity: ${weatherResponse.main.humidity}"
                binding.pressure.text = "Pressure: ${weatherResponse.main.pressure}"

                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())

                var image: Bitmap? = null
                val imageURL =
                    "https://openweathermap.org/img/w/" + weatherResponse.weather[0].icon + ".png"
                executor.execute {
                    try {
                        val `in` = java.net.URL(imageURL).openStream()
                        image = BitmapFactory.decodeStream(`in`)
                        handler.post {
                            binding.weatherIcon.setImageBitmap(image)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })

        // listen on country change from spinner
        binding.countries.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val country = resources.getStringArray(R.array.countries)[position]
                viewModel.getWeatherResponse(country)
                viewModel.getForecastResponse(country)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // init recycler view
        binding.recyclerView.adapter = ForecastAdapter(listOf(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // listen on country change
        viewModel.forecastResponse.observe(this, Observer { forecastResponse ->
            if (forecastResponse != null) {
                // log change
                Log.d("MainActivity", "forecastResponse changed")
                (binding.recyclerView.adapter as ForecastAdapter).changeData(forecastResponse.list)
            }
        })
    }
}