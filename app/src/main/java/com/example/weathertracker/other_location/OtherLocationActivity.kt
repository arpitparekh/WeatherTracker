package com.example.weathertracker.other_location

import android.content.DialogInterface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.weathertracker.R
import com.example.weathertracker.databinding.ActivityOtherLocationBinding
import com.example.weathertracker.databinding.AddPlaceDialogBinding


class OtherLocationActivity : AppCompatActivity() {

    lateinit var binding : ActivityOtherLocationBinding
    lateinit var binding1 : AddPlaceDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .add(R.id.fcv,AddPlacesFragment())
            .addToBackStack("add Place")
            .commit()

        binding.bottomAppBar.setOnClickListener {

            binding1 = AddPlaceDialogBinding.inflate(layoutInflater)

            AlertDialog.Builder(this)
                .setView(binding1.root)
                .setTitle("Please Add Places")
                .setMessage("Please Select City")
                .setPositiveButton("Add", DialogInterface.OnClickListener { dialogInterface, i ->

                }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

                }).create().show()

        }

        val menu =  binding.bottomAppBar.menu
        val spanString = SpannableString(menu[0].title.toString())
        val end = spanString.length
        spanString.setSpan(RelativeSizeSpan(1.5f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menu[0].title = spanString

        binding.bottomAppBar.setOnMenuItemClickListener {


            if(it.itemId==R.id.action_fav_places){

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fcv,FavouritePlacesFragment())
                    .commit()
            }

            true
        }
    }

    override fun onBackPressed() {
        val myFragment: FavouritePlacesFragment? =
            supportFragmentManager.findFragmentByTag("MY_FRAGMENT") as FavouritePlacesFragment?
        if (myFragment != null && myFragment.isVisible()) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv,AddPlacesFragment())
                .commit()

        }else{
            super.onBackPressed()
        }

    }
}