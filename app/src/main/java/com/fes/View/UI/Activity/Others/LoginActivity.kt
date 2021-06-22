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
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.fes.Utils.Utils
import com.fes.View.Interface.AlertTask
import com.fes.View.UI.Activity.Driver.Driver_DashBoard
import com.fes.View.UI.Activity.Others.SignUp.DriverSignUpActivity
import com.fes.View.UI.Activity.Others.SignUp.RiderSignupActivity
import com.fes.View.UI.Activity.Rider.Dashboard_ByRider
import okhttp3.ResponseBody
import org.json.JSONObject
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
            App.instance.mPrefs.setPhoneNo(userNameTxt!!.text.trim().toString());
             Constants.showAlertDialog(this@LoginActivity,userTypeList[0],userTypeList[1],"Login As","",object :AlertTask{
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
            Constants.showAlertDialog(this@LoginActivity,userTypeList[0],userTypeList[1],"SignUp As","",object :AlertTask{
                override fun doInPositiveClick(okBtn: String) {
                    startActivity(Intent(applicationContext, DriverSignUpActivity::class.java))
                }
                override fun doInNegativeClick(cancle: String) {
                    startActivity(Intent(applicationContext, RiderSignupActivity::class.java))
                }
            })



        }
    }

    fun hitLoginApi(okBtn: String) {
      LocalModel.instance!!.showProgressDialog(this@LoginActivity, "Loading..")
        val requestCall: Call<ResponseBody> =
            App.instance!!.apiInterface!!.Userdriverlogin(userNameTxt!!.text.toString(), okBtn, passwordTxt!!.text.toString())
        requestCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {
                    var jsonObject = JSONObject(response!!.body()!!.string())
                    if (jsonObject.getString("result").equals("success")){
                        try {
                            App!!.instance!!.mPrefs!!.setUserID(jsonObject.getString("userId"))
                            App!!.instance!!.mPrefs!!.setUserType(okBtn)
                        } catch (e: Exception) {
                        }
                        if (okBtn.equals(userTypeList[0],true)){
                            startActivity(Intent(applicationContext,Driver_DashBoard::class.java))
                            finish()
                        }
                        if (okBtn.equals(userTypeList[1],true)){
                            startActivity(Intent(applicationContext,Dashboard_ByRider::class.java))
                            finish()
                        }
                    }else{
                        Utils.showToast(applicationContext,jsonObject.getString("status"),Constants.MIDDLE_LONG)
                    }

                } else {
                    Utils.showToast(applicationContext,resources.getString(R.string.null_Response),Constants.MIDDLE_LONG)
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),Constants.MIDDLE_LONG)
            }
        })

    }





}