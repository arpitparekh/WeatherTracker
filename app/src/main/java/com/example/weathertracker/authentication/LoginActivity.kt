package com.example.weathertracker.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.weathertracker.HomeActivity
import com.example.weathertracker.MyGithubActivity
import com.example.weathertracker.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var auth : FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null){
            startActivity(Intent(this,HomeActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        binding.tvGithub.setOnClickListener {
            startActivity(Intent(this,MyGithubActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {

            val email = binding.edtLogin.text.toString()
            val password = binding.edtPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){

                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    startActivity(Intent(this,HomeActivity::class.java))
                    Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show()

                }.addOnFailureListener {
                    Snackbar.make(binding.root,it.message.toString(),Snackbar.LENGTH_SHORT).show()
                }

            }else{
                Snackbar.make(binding.root,"Empty Field",Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}