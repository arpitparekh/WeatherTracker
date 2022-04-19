package com.example.weathertracker.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertracker.databinding.WeatherRowItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WeatherAdapter(val list : ArrayList<Weather>, private val listener: TextClickListener) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth

    class WeatherViewHolder(val binding: WeatherRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("FavData").child(auth.currentUser!!.uid)

        val binding: WeatherRowItemBinding =
            WeatherRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeatherViewHolder(binding)

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        var key : String? = null
        val weather = list[position]
        holder.binding.obj = weather

        ref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                for(childSnap in snapshot.children){

                  if(childSnap.value!! == weather.location?.name && childSnap.exists()) {

                      holder.binding.cbFav.isChecked = true

                      key = childSnap.key
                  }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

        holder.binding.tvQuality.setOnClickListener {
            listener.onAirQualityClick(position)
        }
        holder.binding.tvForecast.setOnClickListener {
            listener.onForecastClick(position)
        }

        holder.binding.cbFav.setOnClickListener {
            listener.onFavCheckBoxClick(position,key,holder.binding.cbFav)
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}