package com.fes.View.UI.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fes.databinding.ActivityFormBinding
import android.content.Intent
import android.widget.Button
import com.fes.View.UI.Activity.Rider.Rider_DashboardActivity

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    private lateinit var submitBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        submitBtn = binding.btnSend
        submitBtn.setOnClickListener {
            val intent = Intent(this, Rider_DashboardActivity::class.java)
            startActivity(intent)
        }
    }


}