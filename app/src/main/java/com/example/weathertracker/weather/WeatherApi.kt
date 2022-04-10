package com.example.weathertracker.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    @Headers("X-RapidAPI-Host: community-open-weather-map.p.rapidapi.com",
    "X-RapidAPI-Key: 6bdba88936msh4cfcc257a36d9ebp12f3e9jsne224939e6077")
    fun getWeatherData(@Query("q")address:String,@Query("units")units:String) : Call<Weather>

}