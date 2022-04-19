package com.example.weathertracker.other_location

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertracker.R
import com.example.weathertracker.databinding.FragmentAddPlacesBinding
import com.example.weathertracker.weather.ApiCall
import com.example.weathertracker.weather.Weather
import com.example.weathertracker.weather.WeatherDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlacesFragment : Fragment(), HideFavouriteIcon {

    lateinit var binding : FragmentAddPlacesBinding
    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth
    private lateinit var api : ApiCall
    lateinit var list : ArrayList<Weather>
    lateinit var adapter : PlacesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddPlacesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewPlaces.layoutManager = LinearLayoutManager(context)

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("FavData").child(auth.currentUser!!.uid)

        list = ArrayList()
        adapter = PlacesAdapter(list,this)

        api = WeatherDatabase.getInstance()!!

//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for(childSnap in snapshot.children){
//
//                    Log.i("snap",childSnap.value.toString())
//                    makeApiCall(childSnap.value.toString())
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
    }

    private fun makeApiCall(city: String?) {

        list.clear()
        val call = api.showWeather(city!!)

        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {

                binding.tvAddPlaces.visibility = View.GONE
                binding.ivPlaceHolder.visibility = View.GONE

                val weather = response.body()

                Log.i("p",response.body().toString())

                if (weather != null) {

                    list.add(weather)

                    adapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<Weather>, t: Throwable) {

                Log.i("placesError",t.toString())

            }

        })

    }

    override fun hideIcon(cbFav: CheckBox){}  // do not hide
}