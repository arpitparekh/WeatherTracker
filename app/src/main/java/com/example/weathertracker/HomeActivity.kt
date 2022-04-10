package com.example.weathertracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.weathertracker.databinding.ActivityHomeBinding
import com.example.weathertracker.map.MapFragment
import com.example.weathertracker.weather.Weather
import com.example.weathertracker.weather.WeatherAdapter
import com.example.weathertracker.weather.WeatherApi
import com.example.weathertracker.weather.WeatherDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity(), WeatherAdapter.OnRefreshClick {

    lateinit var binding : ActivityHomeBinding
    lateinit var list : ArrayList<Weather>
    lateinit var adapter: WeatherAdapter
    lateinit var api : WeatherApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        list = ArrayList()

        binding.rvWeatherList.layoutManager = LinearLayoutManager(this)

        adapter = WeatherAdapter(list,this)

        binding.rvWeatherList.adapter=adapter

        api = WeatherDatabase.getApiInstance()!!

        makeApiCall()

        supportFragmentManager.beginTransaction()
            .add(R.id.fcvMap,MapFragment())
            .commit()

    }

    private fun makeApiCall() {
        list.clear()

        api.getWeatherData("ahmedabad","metric").enqueue(object : Callback<Weather>{
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {

                val weather = response.body()

                list.add(weather!!)

                adapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<Weather>, t: Throwable) {

                Log.i("Error",t.toString())
            }
        })
    }

    override fun onRefresh(position: Int, ivRefresh: LottieAnimationView, ivRefresh1: ImageView) {
        ivRefresh1.visibility = View.GONE
        ivRefresh.visibility = View.VISIBLE
        ivRefresh.playAnimation()

        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            ivRefresh.pauseAnimation()
            ivRefresh.visibility = View.GONE
            ivRefresh1.visibility = View.VISIBLE
        }, 2000)

        makeApiCall()
    }
}