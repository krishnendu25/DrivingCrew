package com.fes.View.UI.Activity.Rider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fes.databinding.ActivityDashboardBinding
import android.widget.TextView

class Rider_DashboardActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDashboardBinding
    lateinit var booknow1:TextView
    lateinit var booknow2:TextView
    lateinit var booknow3:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        booknow2 = binding.booknow2
        booknow1 = binding.booknow1
        booknow3 = binding.booknow3

        booknow1.setOnClickListener {
            val intent = Intent(this, Car_Booking_Form::class.java)
            startActivity(intent)
        }
        booknow2.setOnClickListener {
            val intent = Intent(this, Car_Booking_Form::class.java)
            startActivity(intent)
        }
        booknow3.setOnClickListener {
            val intent = Intent(this, Car_Booking_Form::class.java)
            startActivity(intent)
        }


    }


}