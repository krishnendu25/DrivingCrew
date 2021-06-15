package com.fes.View.UI.Activity.Driver

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.fes.R
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView


class DriverProfileActivity : AppCompatActivity(),View.OnClickListener {

    private var driveProfileIV: CircleImageView? = null
    private var textInputName: TextInputLayout? = null
    private var editTextName: EditText? = null
    private var textInputEmail: TextInputLayout? = null
    private var editTextEmail: EditText? = null
    private var textInputMobile: TextInputLayout? = null
    private var editTextMobile: EditText? = null
    private var textInputAddress: TextInputLayout? = null
    private var editTextPassword: EditText? = null
    private var textInputExperience: TextInputLayout? = null
    private var editTextconfirmPassword: EditText? = null
    private var driving_licence: ImageView? = null
    private var aadharcardImage: ImageView? = null
    private var cirRegisterButton: CircularProgressButton? = null
    private var onBackBT: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_profile)
        findViews()
    }

    fun selectDriverProfile(view: View) {}

    private fun findViews() {
        driveProfileIV = findViewById<View>(R.id.driveProfileIV) as CircleImageView
        driveProfileIV!!.setOnClickListener(this)
        textInputName = findViewById<View>(R.id.textInputName) as TextInputLayout
        editTextName = findViewById<View>(R.id.editTextName) as EditText
        textInputEmail = findViewById<View>(R.id.textInputEmail) as TextInputLayout
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        textInputMobile = findViewById<View>(R.id.textInputMobile) as TextInputLayout
        editTextMobile = findViewById<View>(R.id.editTextMobile) as EditText
        textInputAddress = findViewById<View>(R.id.textInputAddress) as TextInputLayout
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        textInputExperience = findViewById<View>(R.id.textInputExperience) as TextInputLayout
        editTextconfirmPassword = findViewById<View>(R.id.editTextconfirmPassword) as EditText
        driving_licence = findViewById<View>(R.id.driving_licence) as ImageView
        driving_licence!!.setOnClickListener(this)
        aadharcardImage = findViewById<View>(R.id.aadharcardImage) as ImageView
        aadharcardImage!!.setOnClickListener(this)
        cirRegisterButton = findViewById<View>(R.id.cirRegisterButton) as CircularProgressButton
        onBackBT = findViewById<View>(R.id.onBackBT) as ImageView
        cirRegisterButton!!.setOnClickListener(this)
    }


   override fun onClick(v: View) {
        if (v === cirRegisterButton) {
            // Handle clicks for cirRegisterButton
        }
       if (v === driveProfileIV) {
           // Handle clicks for cirRegisterButton
       }
       if (v === aadharcardImage) {
           // Handle clicks for cirRegisterButton
       }
       if (v === driving_licence) {
           // Handle clicks for cirRegisterButton
       }
    }

}