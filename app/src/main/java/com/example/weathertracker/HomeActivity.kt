package com.example.weathertracker



import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertracker.air_quality.AirQualityActivity
import com.example.weathertracker.weather.TextClickListener
import com.example.weathertracker.databinding.ActivityHomeBinding
import com.example.weathertracker.forecast.ForecastActivity
import com.example.weathertracker.other_location.OtherLocationActivity
import com.example.weathertracker.weather.ApiCall
import com.example.weathertracker.weather.Weather
import com.example.weathertracker.weather.WeatherAdapter
import com.example.weathertracker.weather.WeatherDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
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
import kotlin.system.exitProcess


class HomeActivity : AppCompatActivity(), TextClickListener {

    lateinit var binding: ActivityHomeBinding
    lateinit var list: ArrayList<Weather>
    lateinit var api: ApiCall
    lateinit var adapter: WeatherAdapter
    lateinit var enhancedLocation: Location
    private lateinit var mapboxMap: MapboxMap
    private val navigationLocationProvider = NavigationLocationProvider()
    private var mapboxNavigation: MapboxNavigation? = null
    lateinit var permission: ActivityResultLauncher<Array<String>>
    lateinit var again: ActivityResultLauncher<Intent>
    private var isGranted = false
    private var name: String? = null

    lateinit var ref: DatabaseReference
    lateinit var auth: FirebaseAuth

    private var city: String? = null

    private val locationObserver = object : LocationObserver {
        override fun onNewLocationMatcherResult(locationMatcherResult: LocationMatcherResult) {

            enhancedLocation = locationMatcherResult.enhancedLocation

            navigationLocationProvider.changePosition(
                enhancedLocation,
                locationMatcherResult.keyPoints
            )

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapboxMap = binding.mapView.getMapboxMap()

        binding.animation.playAnimation()

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("FavData").child(auth.currentUser!!.uid)

        again = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {

                if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(
                        this@HomeActivity,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        this@HomeActivity,
                        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    initNavigation()
                } else {

                    Toast.makeText(
                        this@HomeActivity,
                        "Please Give Location Permission\nUnder Permission Section",
                        Toast.LENGTH_SHORT
                    ).show()
                    exitProcess(0)
                }

            })

        permission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),
            ActivityResultCallback {
                val fineLocationGranted: Boolean? = it.getOrDefault(
                    Manifest.permission.ACCESS_FINE_LOCATION, false
                )
                val coarseLocationGranted: Boolean? = it.getOrDefault(
                    Manifest.permission.ACCESS_COARSE_LOCATION, false
                )
                if (fineLocationGranted != null && fineLocationGranted) {

                    initNavigation()

                } else if (coarseLocationGranted != null && coarseLocationGranted) {

                    initNavigation()

                } else {

                    openIntent()

                }
            })

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

        adapter = WeatherAdapter(list, this)

        binding.rvWeatherList.adapter = adapter

        api = WeatherDatabase.getInstance()!!

        initialization()

        binding.cvProfile.setOnClickListener {

        }
        binding.fabLocation.setOnClickListener {

            startActivity(Intent(this, OtherLocationActivity::class.java))

        }

    }

    private fun initialization() {

        initStyle()
        initNavigation()

    }


    @SuppressLint("MissingPermission")
    private fun initNavigation() {

        if (ActivityCompat.checkSelfPermission(
                this@HomeActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@HomeActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )

        } else {
            mapboxNavigation = MapboxNavigation(
                NavigationOptions.Builder(this)
                    .accessToken(getString(R.string.mapbox_access_token))
                    .build()
            ).apply {

                startTripSession(withForegroundService = false)
                registerLocationObserver(locationObserver)

            }
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
                city = weather?.location?.name

                if (binding.animation.isAnimating) {

                    binding.animation.clearAnimation()
                    binding.animation.visibility = View.GONE
                    binding.tvLoding.visibility = View.GONE
                }
                if (weather != null) {

                    list.add(weather)

                    name = weather.location?.name!!

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
        mapboxNavigation?.onDestroy()
        binding.mapView.onStop()
    }

    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
//        mapboxNavigation?.stopTripSession()
//        mapboxNavigation?.unregisterLocationObserver(locationObserver)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            val size: Int = permissions.size

            for (i in 0 until size) {
                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[i] == PackageManager.PERMISSION_GRANTED
                ) {

                    initNavigation()
                    isGranted = true
                } else {

                    showDialog()
                }
            }
        }
    }

    private fun showDialog() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Application Permission Needed")
            builder.setMessage("Without Location Permission Current Weather Will not Work")
            builder.setPositiveButton("Give Permission") { dialogInterface, which ->

                permission.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }

    }

    private fun openIntent() {

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        again.launch(intent)

    }

    override fun onAirQualityClick(position: Int) {

        val i = Intent(this, AirQualityActivity::class.java)
        i.putExtra("lat", enhancedLocation.latitude)
        i.putExtra("long", enhancedLocation.longitude)
        i.putExtra("name", name)
        startActivity(i)
    }

    override fun onForecastClick(position: Int) {
        val i = Intent(this, ForecastActivity::class.java)
        i.putExtra("lat", enhancedLocation.latitude)
        i.putExtra("long", enhancedLocation.longitude)
        i.putExtra("name", name)
        startActivity(i)
    }

    override fun onFavCheckBoxClick(position: Int, key: String?, cbFav: CheckBox) {

        if (cbFav.isChecked) {

            ref.push().setValue(city).addOnSuccessListener {

                adapter.notifyDataSetChanged()

            }.addOnFailureListener {
                Log.i("favError", it.toString())
            }

        } else {

            ref.child(key!!).removeValue()
            adapter.notifyDataSetChanged()
        }

    }
}