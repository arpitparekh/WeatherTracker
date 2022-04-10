package com.example.weathertracker

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertracker.databinding.ActivityMyGithubBinding


class MyGithubActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyGithubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.wvGithub.loadUrl("https://github.com/arpitparekh")

    }

    override fun onBackPressed() {

        if(binding.wvGithub.canGoBack()){
            binding.wvGithub.goBack()
        }else{
            super.onBackPressed()
        }
    }
}