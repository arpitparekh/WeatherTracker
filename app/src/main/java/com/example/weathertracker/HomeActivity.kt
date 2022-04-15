package com.example.weathertracker


import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertracker.databinding.ActivityHomeBinding
import com.example.weathertracker.weather.ApiCall
import com.example.weathertracker.weather.Weather
import com.example.weathertracker.weather.WeatherAdapter
import com.example.weathertracker.weather.WeatherDatabase
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.trip.session.LocationMatcherResult
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.ui.maps.location.NavigationLocationProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity(){

    lateinit var binding : ActivityHomeBinding
    lateinit var list : ArrayList<Weather>
    lateinit var api : ApiCall
    lateinit var adapter: WeatherAdapter
    lateinit var enhancedLocation: Location
    private lateinit var mapboxMap: MapboxMap
    private val navigationLocationProvider = NavigationLocationProvider()
    private lateinit var mapboxNavigation: MapboxNavigation

    private val locationObserver = object : LocationObserver{
        override fun onNewLocationMatcherResult(locationMatcherResult: LocationMatcherResult) {

            enhancedLocation = locationMatcherResult.enhancedLocation

            navigationLocationProvider.changePosition(enhancedLocation,locationMatcherResult.keyPoints)

            updateCamera(enhancedLocation)



        }

        override fun onNewRawLocation(rawLocation: Location) {
            makeApiCall(rawLocation.longitude, rawLocation.latitude)
        }

    }

    private fun updateCamera(enhancedLocation: Location) {
        val mapAnimationOptions = MapAnimationOptions.Builder().duration(500L).build()
        binding.mapView.camera.easeTo(
            CameraOptions.Builder()
                .center(Point.fromLngLat(enhancedLocation.longitude, enhancedLocation.latitude))
                .zoom(12.0)
                .build(),
            mapAnimationOptions
        )

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapboxMap = binding.mapView.getMapboxMap()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.mapView.location.apply {

            setLocationProvider(navigationLocationProvider)

//            locationPuck = LocationPuck2D(bearingImage = ContextCompat.getDrawable(
//                this@HomeActivity, com.mapbox.navigation.R.drawable.mapbox_navigation_puck_icon
//            ))
            enabled = true;
        }

        list = ArrayList()

        supportActionBar?.hide()

        binding.rvWeatherList.layoutManager = LinearLayoutManager(this)

        adapter = WeatherAdapter(list)

        binding.rvWeatherList.adapter =adapter

        api = WeatherDatabase.getInstance()!!

        initialization()

    }

    private fun initialization() {

        initStyle()
        initNavigation()

    }

    @SuppressLint("MissingPermission")
    private fun initNavigation() {

            mapboxNavigation = MapboxNavigation(
                NavigationOptions.Builder(this)
                    .accessToken(getString(R.string.mapbox_access_token))
                    .build()
            ).apply {
                startTripSession()
                registerLocationObserver(locationObserver)
            }

    }

    private fun initStyle() {
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    }

    private fun makeApiCall(longitude: Double, latitude: Double) {

        val call = api.showWeather("$latitude,$longitude")

        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                list.clear()
                val weather = response.body()

                if (weather != null) {

                    binding.animation.clearAnimation()
                    binding.animation.visibility = View.GONE
                    binding.tvLoding.visibility = View.GONE

                    list.add(weather)

                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.i("dataError", t.toString())
            }
        })

    }

    override fun onRestart() {
        super.onRestart()

    }


    @SuppressLint("Lifecycle")
    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    @SuppressLint("Lifecycle")
    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    @SuppressLint("Lifecycle")
    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
        mapboxNavigation.onDestroy()
//        mapboxNavigation.stopTripSession()
//        mapboxNavigation.unregisterLocationObserver(locationObserver)
    }
}