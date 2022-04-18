package com.example.weathertracker.air_quality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertracker.databinding.ActivityAirQualityBinding
import com.example.weathertracker.weather.ApiCall
import com.example.weathertracker.weather.WeatherDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirQualityActivity : AppCompatActivity() {

    lateinit var binding : ActivityAirQualityBinding
    lateinit var list : ArrayList<Forecast>
    lateinit var api : ApiCall
    lateinit var adapter : ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.animation.playAnimation()

        binding.recyclerViewAirQuality.layoutManager = LinearLayoutManager(this)

        list = ArrayList()

        adapter = ForecastAdapter(list,intent.getStringExtra("name"))

        binding.recyclerViewAirQuality.adapter = adapter

        apiCall(intent.getDoubleExtra("lat",0.0),intent.getDoubleExtra("long",0.0))

    }

    private fun apiCall(latitude: Double, longitude: Double) {

        api = WeatherDatabase.getInstance()!!

        val call = api.showAirQuality("$latitude,$longitude")

        call.enqueue(object : Callback<Forecast>{
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {

                list.clear()

                if(binding.animation.isAnimating){

                    binding.animation.clearAnimation()
                    binding.animation.visibility= View.GONE
                }

                val forecast = response.body()

                if (forecast != null) {

                    list.add(forecast)

                    adapter.notifyDataSetChanged()
                }

            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.i("dataError",t.toString())
            }
        })

    }
}