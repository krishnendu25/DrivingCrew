package com.fes.View.UI.Activity.Driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fes.R
import com.fes.View.UI.Activity.Rider.Car_Booking_Form

class Driver_DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver__dash_board)
    }

    fun st1(view: View) {val intent = Intent(this, Ride_Start_Form::class.java)
        startActivity(intent)}
    fun st2(view: View) {val intent = Intent(this, Ride_Start_Form::class.java)
        startActivity(intent)}
    fun st3(view: View) {val intent = Intent(this, Ride_Start_Form::class.java)
        startActivity(intent)}
    fun st4(view: View) {val intent = Intent(this, Ride_Start_Form::class.java)
        startActivity(intent)}
    fun st5(view: View) {val intent = Intent(this, Ride_Start_Form::class.java)
        startActivity(intent)}

}