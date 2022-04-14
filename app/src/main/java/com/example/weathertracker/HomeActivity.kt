package com.example.weathertracker

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertracker.databinding.ActivityHomeBinding
import com.example.weathertracker.weather.ApiCall
import com.example.weathertracker.weather.Weather
import com.example.weathertracker.weather.WeatherAdapter
import com.example.weathertracker.weather.WeatherDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener {

    lateinit var binding : ActivityHomeBinding
    lateinit var list : ArrayList<Weather>
    lateinit var api : ApiCall
    lateinit var map : GoogleMap
    lateinit var adapter: WeatherAdapter
    lateinit var currentLocation: Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.animation.playAnimation()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()

        list = ArrayList()

        supportActionBar?.hide()

        binding.rvWeatherList.layoutManager = LinearLayoutManager(this)

        api = WeatherDatabase.getInstance()!!


    }

    private fun fetchLocation() {

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),101)

            return

        }

        val task : Task<Location> = fusedLocationProviderClient.lastLocation;
        task.addOnSuccessListener {
            if(it!=null){
                currentLocation = it
                val supportFragmentManager : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
                supportFragmentManager.getMapAsync(this)

                makeApiCall(currentLocation.latitude,currentLocation.longitude)
            }

        }

    }

    private fun makeApiCall(latitude: Double, longitude: Double) {


        val call = api.showWeather("$latitude,$longitude")

        call.enqueue(object : Callback<Weather>{
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                list.clear()
                val weather = response.body()

                if(weather!=null){

                    binding.animation.clearAnimation()
                    binding.animation.visibility = View.GONE
                    binding.tvLoding.visibility = View.GONE

                    list.add(weather)

                    adapter = WeatherAdapter(list)

                    binding.rvWeatherList.adapter =adapter

                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.i("dataError",t.toString())
            }

        })

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {

        this.map = map

        val latLng = LatLng(currentLocation.latitude,currentLocation.longitude)



        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true;
        val markerOption = MarkerOptions().position(latLng)
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f))
        map.addMarker(markerOption)

        map.setOnMyLocationButtonClickListener(this)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==101){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        fetchLocation()
        onMapReady(map)
        return true
    }
}