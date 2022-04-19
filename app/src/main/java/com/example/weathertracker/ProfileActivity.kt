package com.example.weathertracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weathertracker.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

class ProfileActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileBinding
    lateinit var auth : FirebaseAuth
    lateinit var storageRef : StorageReference
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}