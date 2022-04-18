package com.example.weathertracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weathertracker.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}