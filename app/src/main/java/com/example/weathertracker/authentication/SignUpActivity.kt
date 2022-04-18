package com.example.weathertracker.authentication

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.weathertracker.MyGithubActivity
import com.example.weathertracker.databinding.ActivityLoginBinding
import com.example.weathertracker.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SignUpActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignUpBinding
    lateinit var auth : FirebaseAuth
    lateinit var ref : DatabaseReference
    lateinit var storageRef : StorageReference
    lateinit var gallery :ActivityResultLauncher<String>
    private var uri : Uri? = null
    lateinit var uid : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gallery = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                uri = it
                binding.ivProfileImage.setImageURI(it)
            })

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("UsersData")


        binding.ivProfileImage.setOnClickListener {
            gallery.launch("image/*")
        }
        supportActionBar?.hide()
        binding.tvGithub.setOnClickListener {
            startActivity(Intent(this,MyGithubActivity::class.java))
        }

        binding.btnSignUp.setOnClickListener {

            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val repeat = binding.edtRepeatPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && repeat.isNotEmpty() && uri!=null){

                if(password == repeat){
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {

                        uid = auth.currentUser!!.uid

                        storageRef = FirebaseStorage.getInstance().getReference("Images").child(uid)

                        storageRef.putFile(uri!!).addOnSuccessListener {

                            storageRef.downloadUrl.addOnSuccessListener {

                                val user = User(name,email,uid,it.toString())

                                ref.push().setValue(user).addOnSuccessListener {

                                    startActivity(Intent(this,LoginActivity::class.java))
                                    Toast.makeText(this,"User Register Successfully",Toast.LENGTH_SHORT).show()

                                }.addOnFailureListener {
                                    Snackbar.make(binding.root,it.message.toString(),Snackbar.LENGTH_SHORT).show()
                                }
                            }

                        }.addOnFailureListener {
                            Snackbar.make(binding.root,it.message.toString(),Snackbar.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Snackbar.make(binding.root,it.message.toString(),Snackbar.LENGTH_SHORT).show()
                    }
                }else{
                    Snackbar.make(binding.root,"Password Does Not Match",Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(binding.root,"Empty Field\nNo Image Selected",Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}