package com.fes.View.UI.Activity.Others

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.fes.databinding.ActivitySignupBinding
import android.widget.Button
import android.widget.TextView
import android.view.View
import android.widget.ImageView
import com.fes.R
import android.widget.PopupMenu
import android.widget.*
import java.util.*;
import com.fes.Constant.Constants
import android.content.Intent

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var onBackBT : ImageView
    private lateinit var userNameTxt : TextView
    private lateinit var userEmailTxt : TextView
    private lateinit var mobileTxt : TextView
    private lateinit var editTextType : TextView
    private lateinit var passworltxt : TextView
    private lateinit var confirmpassworltxt : TextView
    private lateinit var signUpBtn : Button

    val arrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        onBackBT = binding.onBackBT
        userNameTxt = binding.editTextName
        userEmailTxt = binding.editTextEmail
        mobileTxt = binding.editTextMobile
        editTextType = binding.editTextType
        passworltxt = binding.editTextPassword
        confirmpassworltxt = binding.editTextconfirmPassword
        signUpBtn = binding.cirRegisterButton

        arrayList.add("For Driver")
        arrayList.add("Care Rent")

        editTextType.setOnClickListener {
            Constants.showDropDownOfRelation(editTextType, arrayList, this@SignupActivity)
        }
        onBackBT.setOnClickListener {
            finish()
        }
    }


}