package com.gl4.tp5.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gl4.tp5.R
import com.gl4.tp5.models.Forecast
import java.util.Date
import java.util.Locale

class ForecastAdapter(private var data: List<Forecast>, context: Context) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = data[position]
        holder.bind(forecast)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data: List<Forecast>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(forecast: Forecast) {
            val tempInCDay = (forecast.temp.day - 273.15).toInt()
            val tempInCNight = (forecast.temp.night - 273.15).toInt()
            // get name of day of week from timestamp
            val dayOfWeek = java.text.SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(forecast.dt * 1000L))
            itemView.findViewById<TextView>(R.id.forecast_date).text = dayOfWeek
            itemView.findViewById<TextView>(R.id.forecast_temp).text = "$tempInCDay° / $tempInCNight°"
            itemView.findViewById<TextView>(R.id.forecast_desc).text = forecast.weather[0].description
        }
    }
}