package com.example.weathertracker.weather

import com.example.weathertracker.air_quality.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiCall {

    companion object{

        const val API_KEY="bbff1dcace56479da1c180043221304"
    }
    @GET("current.json")
    fun showWeather(@Query("q")cityName : String,@Query("key")key : String = API_KEY) : Call<Weather>

    @PUT("forecast.json")
    fun showAirQuality(@Query("q")cityName : String,@Query("key")key : String = API_KEY,@Query("aqi")aqi : String = "yes") : Call<Forecast>

}