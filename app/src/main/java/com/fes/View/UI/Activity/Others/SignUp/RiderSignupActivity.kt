package com.fes.View.UI.Activity.Others.SignUp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fes.App
import com.fes.Constant.Animation
import com.fes.Constant.Constants
import com.fes.Model.ReponseModel.Registration_api
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.fes.Utils.Utils
import com.fes.Utils.Utils.Companion.cretPart
import com.fes.Utils.Utils.Companion.cretPartFile
import com.fes.View.Interface.AlertTask
import com.fes.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*


class RiderSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var onBackBT : ImageView
    private lateinit var userNameTxt : TextView
    private lateinit var userEmailTxt : TextView
    private lateinit var mobileTxt : TextView
    private lateinit var passworltxt : TextView
    private lateinit var confirmpassworltxt : TextView
    private lateinit var signUpBtn : Button
    var requestCall: Call<Registration_api>? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        signUpBtn!!.setOnClickListener({
            hitSignUpActivity()
        })
    }
    
    private fun hitSignUpActivity() {
        if (validation()){
            LocalModel.instance!!.showProgressDialog(this@RiderSignupActivity, "Loading..")

               requestCall =
                    App.instance!!.apiInterface!!.UserRegistration(cretPart(userNameTxt!!.text.toString()),cretPart(""),cretPart(""),cretPart(""),
                        cretPart(mobileTxt!!.text.trim().toString()),cretPart(userEmailTxt!!.text.trim().toString()),cretPart(passworltxt!!.text.trim().toString()),
                        cretPart(confirmpassworltxt!!.text.trim().toString()),cretPart("Rider"),
                        cretPartFile(drawableToBitmap(resources.getDrawable(R.drawable.ic_user_profile)),"ProfilePhoto"),
                        cretPartFile(drawableToBitmap(resources.getDrawable(R.drawable.ic_adharcard)),"AADHARPhoto"),
                        cretPartFile(drawableToBitmap(resources.getDrawable(R.drawable.ic_adharcard)),"DrivingLicensePhoto"))
            
            requestCall!!.enqueue(object : Callback<Registration_api> {
                override fun onResponse(
                    call: Call<Registration_api>,
                    response: Response<Registration_api>
                ) {
                    if (response.body() != null) {
                        if (response.body()!!.result.equals("success",true)){
                            Constants.showAlertDialog(this@RiderSignupActivity,
                                "OK",
                                "",Constants.userTypeList[1]+" Registration Successfully Completed","Successful",object :
                                    AlertTask {
                                override fun doInPositiveClick(okBtn: String) {
                                    finish()
                                }
                                override fun doInNegativeClick(cancle: String) {
                                   finish()
                                }
                            })

                        }else{
                            Utils.showToast(applicationContext,response.body()!!.status,Constants.MIDDLE_LONG)
                        }
                    } else {
                        Utils.showToast(applicationContext,resources.getString(R.string.null_Response),Constants.MIDDLE_LONG)
                        LocalModel.instance!!.cancelProgressDialog()
                    }
                }
                override fun onFailure(call: Call<Registration_api>, t: Throwable) {
                    LocalModel.instance!!.cancelProgressDialog()
                    Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),Constants.MIDDLE_LONG)
                }
            })




        }
    }

    private fun validation(): Boolean {
        if (userNameTxt!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(userNameTxt!!)
            Utils.showToast(this@RiderSignupActivity,"Please Enter Username",Constants.LONG)
            return false
        }else if (userEmailTxt!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(userEmailTxt!!)
            Utils.showToast(this@RiderSignupActivity,"Please Enter Email",Constants.LONG)
            return false
        }else if (mobileTxt!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(mobileTxt!!)
            Utils.showToast(this@RiderSignupActivity,"Please Enter Mobile",Constants.LONG)
            return false
        }else if (mobileTxt!!.text.trim().length!=10){
            Animation.editText_Sh(mobileTxt!!)
            Utils.showToast(this@RiderSignupActivity,"Please Enter Valid Mobile",Constants.LONG)
            return false
        }else if (passworltxt!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(passworltxt!!)
            Utils.showToast(this@RiderSignupActivity,"Please Enter Password",Constants.LONG)
            return false
        }else if (passworltxt!!.text.length<8){
            Animation.editText_Sh(passworltxt!!)
            Utils.showToast(this@RiderSignupActivity,"Please Enter Valid Password",Constants.LONG)
            return false
        }else if (!confirmpassworltxt!!.text.trim().toString().equals(passworltxt!!.text.trim().toString())){
            Animation.editText_Sh(passworltxt!!)
            Animation.editText_Sh(confirmpassworltxt!!)
            Utils.showToast(this@RiderSignupActivity,"Password Not Machetd",Constants.LONG)
            return false
        }
        return true
    }

    private fun setupView() {
        onBackBT = binding.onBackBT
        userNameTxt = binding.editTextName
        userEmailTxt = binding.editTextEmail
        mobileTxt = binding.editTextMobile
        passworltxt = binding.editTextPassword
        confirmpassworltxt = binding.editTextconfirmPassword
        signUpBtn = binding.cirRegisterButton
        onBackBT.setOnClickListener {
            finish()
        }
    }
    fun drawableToBitmap(drawable: Drawable): File? {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                bitmap= bitmapDrawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        var file: File? = null
        return try {
            file = File(Constants.saveImagetoSDcard(bitmap,this@RiderSignupActivity))
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

}