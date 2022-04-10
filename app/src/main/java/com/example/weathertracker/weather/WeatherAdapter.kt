package com.example.weathertracker.weather

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.weathertracker.databinding.WeatherRowItemBinding

class WeatherAdapter(var list : ArrayList<Weather>,var onRefreshClick: OnRefreshClick) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {


    class WeatherViewHolder(binding : WeatherRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val binding = binding

    }

    public interface OnRefreshClick{
        fun onRefresh(position: Int, ivRefresh: LottieAnimationView, ivRefresh1: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {

        val binding: WeatherRowItemBinding =
            WeatherRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        val weather = list[position]

        holder.binding.obj1=weather

        val handler = Handler(Looper.getMainLooper())

//        handler.postDelayed({
//            holder.binding.ivRefresh.anima
//        }, 2000)

        holder.binding.ivRefresh.setOnClickListener {
            onRefreshClick.onRefresh(position,holder.binding.animationRefresh,holder.binding.ivRefresh)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}