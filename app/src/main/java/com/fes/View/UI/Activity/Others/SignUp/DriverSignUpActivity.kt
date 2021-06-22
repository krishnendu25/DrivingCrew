package com.fes.View.UI.Activity.Others.SignUp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.fes.App
import com.fes.Constant.Animation
import com.fes.Constant.Constants
import com.fes.Model.ReponseModel.Registration_api
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.fes.Utils.Utils
import com.fes.View.Interface.AlertTask
import com.github.dhaval2404.imagepicker.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DriverSignUpActivity : AppCompatActivity(),View.OnClickListener {
    private var driveProfileIV_Parh: String=""
    private var aadharcardImage_Parh: String=""
    private var driving_licence_Parh: String=""
    private val mActivity: Activity?=this@DriverSignUpActivity
    private var flagImage=0
    private var profileName:TextView? = null
    private var driveProfileIV: CircleImageView? = null
    private var editTextName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextMobile: EditText? = null
    private var PresentAddressEd: EditText? = null
    private var editTextAge: EditText? = null
    private var editTextDrivingExperience: EditText? = null
    private var driving_licence: ImageView? = null
    private var aadharcardImage: ImageView? = null
    private var cirRegisterButton: CircularProgressButton? = null
    private var onBackBT: ImageView? = null
    var editTextPassword: EditText? = null
    var editTextconfirmPassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_signup)
        findViews()
    }

    fun selectDriverProfile(view: View) {}

    private fun findViews() {
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextconfirmPassword = findViewById(R.id.editTextconfirmPassword);
        profileName= findViewById<View>(R.id.profileName) as TextView
        driveProfileIV = findViewById<View>(R.id.driveProfileIV) as CircleImageView
        driveProfileIV!!.setOnClickListener(this)
        editTextName = findViewById<View>(R.id.editTextName) as EditText
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextMobile = findViewById<View>(R.id.editTextMobile) as EditText
        editTextAge= findViewById<View>(R.id.editTextAge) as EditText
        PresentAddressEd = findViewById<View>(R.id.editTextAddress) as EditText
        editTextDrivingExperience = findViewById<View>(R.id.editTextDrivingExperience) as EditText
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
           if (validation()){
               hitSignUpDriver(App.instance.mPrefs.getUserID())
           }
        }
       if (v === driveProfileIV) {
         var  filescanBankPassBook = File(
               Environment.getExternalStorageDirectory(),
               "BankPassbook_ImagePicker"
           )
           ImagePicker.with(this)
               .crop()
               .compress(1024)
               .maxResultSize(1080, 1080)
               .saveDir(filescanBankPassBook!!)
               .start()
           flagImage=100
       }
       if (v === aadharcardImage) {
           var  filescanBankPassBook = File(
               Environment.getExternalStorageDirectory(),
               "BankPassbook_ImagePicker"
           )
           ImagePicker.with(this)
               .crop()
               .compress(1024)
               .maxResultSize(1080, 1080)
               .saveDir(filescanBankPassBook!!)
               .start()
           flagImage=101
       }
       if (v === driving_licence) {
           var  filescanBankPassBook = File(
               Environment.getExternalStorageDirectory(),
               "BankPassbook_ImagePicker"
           )
           ImagePicker.with(this)
               .crop()
               .compress(1024)
               .maxResultSize(1080, 1080)
               .saveDir(filescanBankPassBook!!)
               .start()
           flagImage=102
       }
    }

    private fun validation(): Boolean {
        if (editTextName!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextName!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Enter Username",Constants.LONG)
            return false
        }else if (editTextEmail!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextEmail!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Enter Email",Constants.LONG)
            return false
        }else if (editTextMobile!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextMobile!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Enter Mobile",Constants.LONG)
            return false
        }else if (editTextMobile!!.text.trim().length!=10){
            Animation.editText_Sh(editTextMobile!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Enter Valid Mobile",Constants.LONG)
            return false
        }else if (editTextAge!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextAge!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Enter Password",Constants.LONG)
            return false
        }else if (editTextDrivingExperience!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextDrivingExperience!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Select UserType",Constants.LONG)
            return false
        }else if(driveProfileIV_Parh.isNullOrEmpty()){
            Animation.editText_Sh(driveProfileIV!!)
            return false
        }else if(aadharcardImage_Parh.isNullOrEmpty()){
            Utils.showToast(this@DriverSignUpActivity,"Please Select Aadhar Card",Constants.LONG)
            return false
        }else if(driving_licence_Parh.isNullOrEmpty()){
            Utils.showToast(this@DriverSignUpActivity,"Please Select Driving Licence",Constants.LONG)
            return false
        }else if (editTextPassword!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextPassword!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Enter Password",Constants.LONG)
            return false
        }else if (editTextPassword!!.text.length<8){
            Animation.editText_Sh(editTextPassword!!)
            Utils.showToast(this@DriverSignUpActivity,"Please Enter Valid Password",Constants.LONG)
            return false
        }else if (!editTextconfirmPassword!!.text.trim().toString().equals(editTextPassword!!.text.trim().toString())){
            Animation.editText_Sh(editTextPassword!!)
            Animation.editText_Sh(editTextconfirmPassword!!)
            Utils.showToast(this@DriverSignUpActivity,"Password Not Machetd",Constants.LONG)
            return false
        }
        return true
    }


    private fun hitSignUpDriver(userID: String) {
        LocalModel.instance!!.showProgressDialog(this@DriverSignUpActivity, "Loading..")
        var requestCall: Call<Registration_api>? =
                App.instance!!.apiInterface!!.DriverRegistration(
                    Utils.cretPart(editTextName!!.text.toString()),
                    Utils.cretPart(editTextAge!!.text.toString()),
                    Utils.cretPart(editTextDrivingExperience!!.text.toString()),
                    Utils.cretPart(PresentAddressEd!!.text.toString()),
                    Utils.cretPart(editTextMobile!!.text.toString()),
                    Utils.cretPart(editTextEmail!!.text.toString()),
                    Utils.cretPart(editTextPassword!!.text.toString()),
                    Utils.cretPart(Constants.userTypeList[0]),
                    Utils.cretPartFile(File(driveProfileIV_Parh),"ProfilePhoto"),
                    Utils.cretPartFile(File(aadharcardImage_Parh),"AADHARPhoto"),
                    Utils.cretPartFile(File(driving_licence_Parh),"DrivingLicensePhoto")
                    )
        
        requestCall!!.enqueue(object : Callback<Registration_api> {
            override fun onResponse(
                call: Call<Registration_api>,
                response: Response<Registration_api>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {
                    if (response.body()!!.result.equals("success",true)){
                        Constants.showAlertDialog(this@DriverSignUpActivity,
                            "OK",
                            "",Constants.userTypeList[0]+" Registration Successfully Completed","Successful",object :
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
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),
                    Constants.MIDDLE_LONG)
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            when(flagImage){
                102->{
                    try {
                        val imageUri: Uri = data?.data!!
                        if (imageUri!=null)
                            driving_licence!!.setImageURI(imageUri)
                        driving_licence_Parh = Constants.saveImagetoSDcard(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri),mActivity!!)
                            .toString()
                    } catch (e: Exception) {
                    }
                }
                101->{
                    try {
                        val imageUri: Uri = data?.data!!
                        if (imageUri!=null)
                            aadharcardImage!!.setImageURI(imageUri)
                        aadharcardImage_Parh = Constants.saveImagetoSDcard( MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri),mActivity!!)
                            .toString()
                    } catch (e: Exception) {
                    }
                }
                100->{
                    try {
                        val imageUri: Uri = data?.data!!
                        if (imageUri!=null)
                            driveProfileIV!!.setImageURI(imageUri)
                        driveProfileIV_Parh = Constants.saveImagetoSDcard( MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri),mActivity!!)
                            .toString()
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }


}