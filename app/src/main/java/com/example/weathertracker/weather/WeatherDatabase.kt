package com.example.weathertracker.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherDatabase {

    companion object{

        var api : WeatherApi? = null

        fun getApiInstance() : WeatherApi?{

            if(api==null){

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://community-open-weather-map.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

                api = retrofit.create(WeatherApi::class.java)

            }

            return api
        }
    }
}