package com.example.weathertracker.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weathertracker.MyGithubActivity
import com.example.weathertracker.databinding.ActivityLoginBinding
import com.example.weathertracker.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvGithub.setOnClickListener {
            startActivity(Intent(this,MyGithubActivity::class.java))
        }
    }
}