package com.example.weathertracker.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.air_quality.HourItem
import com.example.weathertracker.databinding.ForecastRowItemBinding

class ForecastAdapter(private val list: ArrayList<HourItem?>) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(val binding: ForecastRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

        val hour = list[position]
        holder.binding.obj = hour
    }

    override fun getItemCount(): Int {
        return list.size
    }
}