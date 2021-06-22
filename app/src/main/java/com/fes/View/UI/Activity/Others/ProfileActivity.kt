package com.fes.View.UI.Activity.Others

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
import com.fes.Model.ReponseModel.EditUserDetails_api
import com.fes.Model.ReponseModel.UserDetails_Api
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.fes.Utils.Utils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileActivity : AppCompatActivity(),View.OnClickListener {
    private var driveProfileIV_Parh: String=""
    private var aadharcardImage_Parh: String=""
    private var driving_licence_Parh: String=""
    private val mActivity: Activity?=this@ProfileActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_profile)
        findViews()
        getAllDetails()
    }

    fun selectDriverProfile(view: View) {}

    private fun findViews() {
        profileName= findViewById<View>(R.id.profileName) as TextView
        driveProfileIV = findViewById<View>(R.id.driveProfileIV) as CircleImageView
        driveProfileIV!!.setOnClickListener(this)
        editTextName = findViewById<View>(R.id.editTextName) as EditText
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextMobile = findViewById<View>(R.id.editTextMobile) as EditText
        editTextMobile!!.setText(App.instance.mPrefs.getPhoneNo());
        editTextAge= findViewById<View>(R.id.editTextAge) as EditText
        PresentAddressEd = findViewById<View>(R.id.editTextAddress) as EditText
        editTextDrivingExperience = findViewById<View>(R.id.editTextDrivingExperience)  as EditText
        driving_licence = findViewById<View>(R.id.driving_licence) as ImageView
        driving_licence!!.setOnClickListener(this)
        aadharcardImage = findViewById<View>(R.id.aadharcardImage) as ImageView
        aadharcardImage!!.setOnClickListener(this)
        cirRegisterButton = findViewById<View>(R.id.cirRegisterButton) as CircularProgressButton
        onBackBT = findViewById<View>(R.id.onBackBT) as ImageView
        cirRegisterButton!!.setOnClickListener(this)
        profileName!!.text=App.instance.mPrefs.getUserType()+" Profile"
    }


   override fun onClick(v: View) {
        if (v === cirRegisterButton) {
           if (validation()){
               hitUpdateMyDetails(App.instance.mPrefs.getUserType(),App.instance.mPrefs.getUserID())
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
            Utils.showToast(this@ProfileActivity,"Please Enter Username",Constants.LONG)
            return false
        }else if (editTextEmail!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextEmail!!)
            Utils.showToast(this@ProfileActivity,"Please Enter Email",Constants.LONG)
            return false
        }else if (editTextMobile!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextMobile!!)
            Utils.showToast(this@ProfileActivity,"Please Enter Mobile",Constants.LONG)
            return false
        }else if (editTextMobile!!.text.trim().length!=10){
            Animation.editText_Sh(editTextMobile!!)
            Utils.showToast(this@ProfileActivity,"Please Enter Valid Mobile",Constants.LONG)
            return false
        }else if (editTextAge!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextAge!!)
            Utils.showToast(this@ProfileActivity,"Please Enter Password",Constants.LONG)
            return false
        }else if (editTextDrivingExperience!!.text.trim().isNullOrEmpty()){
            Animation.editText_Sh(editTextDrivingExperience!!)
            Utils.showToast(this@ProfileActivity,"Please Select UserType",Constants.LONG)
            return false
        }else if(driveProfileIV_Parh.isNullOrEmpty()){
            Animation.editText_Sh(driveProfileIV!!)
            return false
        }else if(aadharcardImage_Parh.isNullOrEmpty()){
            Utils.showToast(this@ProfileActivity,"Please Select Aadhar Card",Constants.LONG)
            return false
        }else if(driving_licence_Parh.isNullOrEmpty()){
            Utils.showToast(this@ProfileActivity,"Please Select Driving Licence",Constants.LONG)
            return false
        }
        return true
        
        
    }

    private fun hitUpdateMyDetails(userType: String, userID: String) {
        LocalModel.instance!!.showProgressDialog(this@ProfileActivity, "Loading..")
        var requestCall: Call<EditUserDetails_api>? =null
        if (userType.equals(Constants.userTypeList[0])){
            requestCall =
                App.instance!!.apiInterface!!.EditDriverDetailsById(
                    Utils.cretPart(userID),
                    Utils.cretPart(editTextName!!.text.toString()),
                    Utils.cretPart(editTextAge!!.text.toString()),
                    Utils.cretPart(PresentAddressEd!!.text.toString()),
                    Utils.cretPart(editTextMobile!!.text.toString()),
                    Utils.cretPart(editTextEmail!!.text.toString()),
                    Utils.cretPart(userType),
                    Utils.cretPartFile(File(driveProfileIV_Parh),"ProfilePhoto"),
                    Utils.cretPartFile(File(aadharcardImage_Parh),"AADHARPhoto"),
                    Utils.cretPartFile(File(driving_licence_Parh),"DrivingLicensePhoto"), Utils.cretPart(editTextDrivingExperience!!.text.toString()))

        }else {
            requestCall =
                App.instance!!.apiInterface!!.EditRiderDetailsById(
                    Utils.cretPart(userID),
                    Utils.cretPart(editTextName!!.text.toString()),
                    Utils.cretPart(editTextAge!!.text.toString()),
                    Utils.cretPart(PresentAddressEd!!.text.toString()),
                    Utils.cretPart(editTextMobile!!.text.toString()),
                    Utils.cretPart(editTextEmail!!.text.toString()),
                    Utils.cretPart(userType),
                    Utils.cretPartFile(File(driveProfileIV_Parh),"ProfilePhoto"),
                    Utils.cretPartFile(File(aadharcardImage_Parh),"AADHARPhoto"),
                    Utils.cretPartFile(File(driving_licence_Parh),"DrivingLicensePhoto"), Utils.cretPart(editTextDrivingExperience!!.text.toString()))
        }

        
        requestCall.enqueue(object : Callback<EditUserDetails_api> {
            override fun onResponse(
                call: Call<EditUserDetails_api>,
                response: Response<EditUserDetails_api>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {
                   if (response!!.body()!!.result.equals("success",true)){
                       getAllDetails() 
                   }else{
                       Utils.showToast(applicationContext,response!!.body()!!.status,Constants.MIDDLE_LONG)
                   }
                }else{
                    Utils.showToast(applicationContext,resources.getString(R.string.null_Response),Constants.MIDDLE_LONG)
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }
            override fun onFailure(call: Call<EditUserDetails_api>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),
                    Constants.MIDDLE_LONG)
            }
        })

    }

    fun getAllDetails(){
        
        LocalModel.instance!!.showProgressDialog(this@ProfileActivity, "Loading..")
        val requestCall: Call<UserDetails_Api> =
            App.instance!!.apiInterface!!.getuserDetailsById(App!!.instance!!.mPrefs.getUserID(),App!!.instance!!.mPrefs.getUserType())
        requestCall.enqueue(object : Callback<UserDetails_Api> {
            override fun onResponse(
                call: Call<UserDetails_Api>,
                response: Response<UserDetails_Api>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {
                  setUserDetails(response.body()!!)
                }
            }
            override fun onFailure(call: Call<UserDetails_Api>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),
                    Constants.MIDDLE_LONG)
            }
        })
        
        
        
        
    }

    private fun setUserDetails(body: UserDetails_Api) {
        if(!body!!.Name.isNullOrBlank())
        editTextName!!.setText(body!!.Name)
        if(!body!!.EmailId.isNullOrBlank())
        editTextEmail!!.setText(body!!.EmailId)
        if(!body!!.PhoneNumber.isNullOrBlank())
        editTextMobile!!.setText(body!!.PhoneNumber)
        if(!body!!.PresentAddress.isNullOrBlank())
        PresentAddressEd!!.setText(body!!.PresentAddress)
        if(!body!!.DrivingExperience.isNullOrBlank())
        editTextDrivingExperience!!.setText(body!!.DrivingExperience)
        if(!body!!.Age.isNullOrBlank())
        editTextAge!!.setText(body!!.Age)
        try {
            if (body.DrivingLicensePhoto.contains("http"))
            Picasso.get()
                .load(body.DrivingLicensePhoto)
                .into(driving_licence);
        } catch (e: Exception) {
        }
        try {
            if (body.DrivingLicensePhoto.contains("http"))
            Picasso.get()
                .load(body.AADHARPhoto)
                .into(aadharcardImage);
        } catch (e: Exception) {
        }
        try {
            if (body.ProfilePhoto.contains("http"))
            Picasso.get()
                .load(body.ProfilePhoto)
                .into(driveProfileIV);
        } catch (e: Exception) {
        }

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