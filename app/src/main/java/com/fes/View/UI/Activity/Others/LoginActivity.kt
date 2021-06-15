package com.fes.View.UI.Activity.Others

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fes.databinding.ActivityLoginBinding
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.content.Intent
import com.fes.App
import com.fes.Constant.Animation
import com.fes.Constant.Constants
import com.fes.Constant.Constants.obj.userTypeList
import com.fes.Model.ReponseModel.Userdriverlogin_api
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.fes.Utils.Utils
import com.fes.View.Interface.AlertTask
import com.fes.View.UI.Activity.Driver.Driver_DashBoard
import com.fes.View.UI.Activity.Rider.Rider_DashboardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            }else{

             Constants.showAlertDialog(this@LoginActivity,userTypeList[0],userTypeList[1],"Which One You are?","Please Select",object :AlertTask{
                 override fun doInPositiveClick(okBtn: String) {
                     hitLoginApi(okBtn)
                 }
                 override fun doInNegativeClick(cancle: String) {
                     hitLoginApi(cancle)
                 }
             })

            }

        }

        signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    fun hitLoginApi(okBtn: String) {
    //    LocalModel.instance!!.showProgressDialog(this@LoginActivity, "Loading..")
        val requestCall: Call<Userdriverlogin_api> =
            App.instance!!.apiInterface!!.Userdriverlogin(userNameTxt!!.text.toString(), okBtn, passwordTxt!!.text.toString())
        requestCall.enqueue(object : Callback<Userdriverlogin_api> {
            override fun onResponse(
                call: Call<Userdriverlogin_api>,
                response: Response<Userdriverlogin_api>
            ) {
                if (response.body() != null) {
                    if (response.body()!!.result.equals("success",true)){
                        if (okBtn.equals(userTypeList[0],true)){
                            Intent(applicationContext,Driver_DashBoard::class.java)
                        }
                        if (okBtn.equals(userTypeList[1],true)){
                            Intent(applicationContext,Rider_DashboardActivity::class.java)
                        }
                    }else{
                        Utils.showToast(applicationContext,response.body()!!.status,Constants.MIDDLE_LONG)
                    }
                } else {
                    Utils.showToast(applicationContext,resources.getString(R.string.null_Response),Constants.MIDDLE_LONG)
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }
            override fun onFailure(call: Call<Userdriverlogin_api>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),Constants.MIDDLE_LONG)
            }
        })

    }





}