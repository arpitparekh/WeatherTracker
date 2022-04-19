package com.example.weathertracker.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weathertracker.air_quality.Forecast
import com.example.weathertracker.air_quality.HourItem
import com.example.weathertracker.databinding.ActivityForecastBinding
import com.example.weathertracker.weather.ApiCall
import com.example.weathertracker.weather.WeatherDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {

    lateinit var binding : ActivityForecastBinding
    lateinit var list : ArrayList<HourItem?>
    lateinit var api : ApiCall
    private lateinit var adapter : ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.animation.playAnimation()

        supportActionBar?.hide()

        binding.recyclerViewForecast.layoutManager = GridLayoutManager(this,2)

        list= ArrayList()

        adapter = ForecastAdapter(list)

        binding.recyclerViewForecast.adapter = adapter

        api = WeatherDatabase.getInstance()!!

        val call = api.showAirQuality("ahmedabad")

        call.enqueue(object : Callback<Forecast>{
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {

                binding.animation.clearAnimation()
                binding.animation.visibility = View.GONE

                val forecast = response.body()

                if(forecast!=null){
                    for(item in forecast.forecast?.forecastday!![0]?.hour!!){

                        if (item != null) {

                            item.time = item.time!!.split(" ")[1]
                        }

                        list.add(item)

                    }
                    adapter.notifyDataSetChanged()
                }

                Log.i("list",list.toString())

                adapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {

            }


        })

    }
}