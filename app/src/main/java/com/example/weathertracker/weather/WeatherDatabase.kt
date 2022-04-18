package com.example.weathertracker.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherDatabase {

    companion object{

        fun getInstance(): ApiCall? {

            val retrofit = Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiCall::class.java)
        }

    }
}