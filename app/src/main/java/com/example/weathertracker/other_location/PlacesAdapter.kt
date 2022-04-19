package com.example.weathertracker.other_location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.databinding.WeatherRowItemBinding
import com.example.weathertracker.weather.Weather

class PlacesAdapter(private val list : ArrayList<Weather>,val hideFavouriteIcon: HideFavouriteIcon) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    class PlacesViewHolder(val binding: WeatherRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val weather = list[position]
        holder.binding.obj = weather

        hideFavouriteIcon.hideIcon(holder.binding.cbFav)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}