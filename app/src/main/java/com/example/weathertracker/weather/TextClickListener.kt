package com.example.weathertracker.weather

import android.widget.CheckBox
import android.widget.CompoundButton

interface TextClickListener {

    fun onAirQualityClick(position : Int)
    fun onForecastClick(position : Int)
    fun onFavCheckBoxClick(position: Int, key: String?, cbFav: CheckBox)
}