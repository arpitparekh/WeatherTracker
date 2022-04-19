package com.example.weathertracker.air_quality

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.databinding.AirQualityRowItemBinding

class ForecastAdapter(private val list: ArrayList<Forecast>, private val name: String?) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(val binding: AirQualityRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {

        val binding = AirQualityRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ForecastViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = list[position]

        holder.binding.tvCo.text = "Carbon Monoxide : ${(forecast.current?.airQuality?.co).toString().substring(0,5)} μg/m3"
        holder.binding.tvOzone.text = "Ozone : ${(forecast.current?.airQuality?.o3).toString().substring(0,4)} μg/m3"
        holder.binding.tvNo.text = "Nitrogen dioxide : ${(forecast.current?.airQuality?.no2).toString().substring(0,4)} μg/m3"
        holder.binding.tvSulphur.text = "Sulphur dioxide : ${(forecast.current?.airQuality?.so2).toString().substring(0,3)} μg/m3"

        if(holder.binding.tvPM25.text.length>3){
            holder.binding.tvPM25.text = "PM2.5 : ${(forecast.current?.airQuality?.pm25).toString().substring(0,5)} μg/m3"
        }else{
            holder.binding.tvPM25.text = "PM2.5 : ${(forecast.current?.airQuality?.pm25).toString().substring(0,4)} μg/m3"
        }

        if(holder.binding.tvPM10.text.length>3){
            holder.binding.tvPM10.text = "PM10 : ${(forecast.current?.airQuality?.pm10).toString().substring(0,5)} μg/m3"
        }else if(holder.binding.tvPM10.text.length ==3){
            holder.binding.tvPM10.text = "PM10 : ${(forecast.current?.airQuality?.pm10).toString().substring(0,3)} μg/m3"
        }else{
            holder.binding.tvPM10.text = "PM10 : ${(forecast.current?.airQuality?.pm10).toString().substring(0,3)} μg/m3"
        }

        holder.binding.tvUv.text = "UV index : ${(forecast.current?.uv).toString()}"

        holder.binding.tvName.text = forecast.location?.name

        when(forecast.current?.airQuality?.usEpaIndex){

            1-> holder.binding.tvResult.text = "Air Quality is Good"
            2-> holder.binding.tvResult.text = "Air Quality is Moderate"
            3-> holder.binding.tvResult.text = "Air Quality is Unhealthy for sensitive group"
            4-> holder.binding.tvResult.text = "Air Quality is Unhealthy"
            5-> holder.binding.tvResult.text = "Air Quality is Very Unhealthy"
            6-> holder.binding.tvResult.text = "Air Quality is Hazardous"
            else-> holder.binding.tvResult.text = "Fetching Error"
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}