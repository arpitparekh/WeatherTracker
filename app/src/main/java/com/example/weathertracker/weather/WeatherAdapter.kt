package com.example.weathertracker.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.databinding.WeatherRowItemBinding

class WeatherAdapter(val list : ArrayList<Weather>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    class WeatherViewHolder(binding: WeatherRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {

        val binding: WeatherRowItemBinding =
            WeatherRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeatherViewHolder(binding)

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = list[position]

        holder.binding.obj = weather
    }

    override fun getItemCount(): Int {

        if(list==null){
            return 1
        }else{
            return list.size
        }
    }
}