package com.example.weathertracker.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherDatabase {

    companion object{

        var api : ApiCall? = null

        fun getInstance() : ApiCall? {

            if(api==null){

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://api.weatherapi.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                api = retrofit.create(ApiCall::class.java)
            }

            return api

        }

    }
}