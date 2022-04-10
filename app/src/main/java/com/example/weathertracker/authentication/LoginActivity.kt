package com.example.weathertracker.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.weathertracker.HomeActivity
import com.example.weathertracker.MyGithubActivity
import com.example.weathertracker.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        binding.tvGithub.setOnClickListener {
            startActivity(Intent(this,MyGithubActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}