package com.fes.View.UI.Activity.Others

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fes.databinding.ActivityLoginBinding
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.content.Intent
import com.fes.Constant.Animation
import com.fes.Constant.Constants
import com.fes.Utils.Utils
import com.fes.View.UI.Activity.Driver.Driver_DashBoard
import com.fes.View.UI.Activity.FormActivity
import com.fes.View.UI.Activity.Rider.Rider_DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var userNameTxt : TextView
    private lateinit var passwordTxt : TextView
    private lateinit var facebookImg : ImageView
    private lateinit var linkdinImg : ImageView
    private lateinit var twitterImg : ImageView
    private lateinit var registerText : TextView
    private lateinit var signupText : TextView
    private lateinit var LoginBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        userNameTxt = binding.userName
        passwordTxt = binding.passWord
//        facebookImg = binding.facebook
//        linkdinImg = binding.linkdin
//        twitterImg = binding.twitter
        LoginBtn = binding.login
        registerText = binding.register
        signupText = binding.signup

        LoginBtn.setOnClickListener {
            if (userNameTxt!!.text.trim().isNullOrEmpty()){
                Animation.editText_Sh(userNameTxt!!)
                Utils.showToast(this@LoginActivity,"Please Enter Username",Constants.LONG)
            }else if (passwordTxt!!.text.trim().isNullOrEmpty()){
                Animation.editText_Sh(passwordTxt!!)
                Utils.showToast(this@LoginActivity,"Please Enter Password",Constants.LONG)
            }else if (userNameTxt!!.text.toString().equals("ride",true) && passwordTxt!!.text.toString().equals("1234",true)){
                val intent = Intent(this, Rider_DashboardActivity::class.java)
                startActivity(intent)
            }else if (userNameTxt!!.text.toString().equals("drive",true) && passwordTxt!!.text.toString().equals("1234",true)){
                val intent = Intent(this, Driver_DashBoard::class.java)
                startActivity(intent)
            }else{
                Utils.showToast(this@LoginActivity,"Please Enter Valid Login Credential",Constants.LONG)
            }




        }

        signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

}