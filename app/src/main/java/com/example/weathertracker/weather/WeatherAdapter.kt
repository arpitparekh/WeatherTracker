package com.example.weathertracker.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.air_quality.TextClickListener
import com.example.weathertracker.databinding.WeatherRowItemBinding

class WeatherAdapter(val list : ArrayList<Weather>, private val listener: TextClickListener) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(val binding: WeatherRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {

        val binding: WeatherRowItemBinding =
            WeatherRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeatherViewHolder(binding)

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = list[position]

        holder.binding.obj = weather

        holder.binding.tvQuality.setOnClickListener {

            listener.onAirQualityClick(position)

        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}