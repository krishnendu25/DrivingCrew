package com.fes.View.UI.Activity.Rider

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.WingSure.ServiceMannager.ApiCallingHelper.ApiCallHelperPresenter
import com.WingSure.ServiceMannager.ApiCallingHelper.ApiResponseCallBack
import com.bumptech.glide.Glide
import com.fes.App
import com.fes.Constant.Animation
import com.fes.Constant.Constants
import com.fes.R
import com.fes.Utils.DateTimePicker
import com.fes.Utils.ToastUtils
import com.fes.Utils.Utils
import com.fes.View.Interface.AlertTask
import com.fes.View.Interface.TimeDateCallBack
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*


class CarBookingForm_ByRider : AppCompatActivity() {
    var carNameTV: TextView? = null
    var carImageIV: ImageView? = null
    var milageTV: TextView? = null
    var selectYouTV: TextView? = null
    var sitNumTV: TextView? = null
    var fuleTypeTV: TextView? = null
    var startDateTV: TextView? = null
    var startTimeTV: TextView? = null
    var endDateTV: TextView? = null
    var endTimeTV: TextView? = null
    var sourceLocationTV: TextView? = null
    var endLocationTV: TextView? = null
    var rentingAmmountTV: TextView? = null
    var mApiCallHelperPresenter: ApiCallHelperPresenter? = null
    var vehicleNumberView: LinearLayout? = null
    var vehicleNumberED: EditText? = null

    var PassengersED: EditText? = null
    protected val TAG = "LocationOnOff"
    val MY_PERMISSIONS_REQUEST_LOCATION=569
    private var googleApiClient: GoogleApiClient? = null
    val REQUEST_LOCATION = 199
    var datePicker: DateTimePicker? = null
   var brandName: String? = null
    var carImage: String? = null
    var carName: String? = null
    var id: String? = null
    var pricePerKm: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car__booking__form)
        iniView(this@CarBookingForm_ByRider)
        checkLocationPermission()
    }
    private fun iniView(mActivity: Activity) {
        try {
            brandName =intent.getStringExtra("brandName")
        } catch (e: Exception) {
        }
        try {
            carImage =intent.getStringExtra("carImage")
        } catch (e: Exception) {
        }
        try {
            carName=intent.getStringExtra("carName")
        } catch (e: Exception) {
        }
        try {
            id=intent.getStringExtra("id")
        } catch (e: Exception) {
        }
        try {
            pricePerKm=intent.getStringExtra("pricePerKm")
        } catch (e: Exception) {
        }
        carNameTV = findViewById(R.id.carNameTV);
        carNameTV!!.text =brandName+" "+carName
        carImageIV = findViewById(R.id.carImageIV);
        try {
            Glide.with(this@CarBookingForm_ByRider).load(carImage).placeholder(R.drawable.car1).into(carImageIV!!)
        } catch (e: Exception) {
        }
        milageTV = findViewById(R.id.milageTV);
        milageTV!!.text =pricePerKm
        sitNumTV = findViewById(R.id.sitNumTV);
        selectYouTV = findViewById(R.id.selectYouTV);
        fuleTypeTV = findViewById(R.id.fuleTypeTV);
        startDateTV = findViewById(R.id.startDateTV);
        startTimeTV = findViewById(R.id.startTimeTV);
        endDateTV = findViewById(R.id.endDateTV);
        endTimeTV = findViewById(R.id.endTimeTV);
        PassengersED = findViewById(R.id.PassengersED);
        sourceLocationTV = findViewById(R.id.sourceLocationTV);
        endLocationTV = findViewById(R.id.endLocationTV);
        rentingAmmountTV = findViewById(R.id.rentingAmmountTV);
        vehicleNumberView =  findViewById(R.id.vehicleNumberView);
        vehicleNumberED =  findViewById(R.id.vehicleNumberED);
        datePicker = DateTimePicker(this)
        mApiCallHelperPresenter = ApiCallHelperPresenter(this,object: ApiResponseCallBack{
            override fun successResponse(result: ResponseBody, requestCode: Int) {
                
            }
            override fun successResponse(jsonObject: JSONObject, requestCode: Int) {
                if (requestCode == 123) {
                    if(jsonObject.getString("result").equals("success")){
                        Constants.showAlertDialog(this@CarBookingForm_ByRider,
                            "OK",
                            "",
                            "Car Booking Successfully",
                            jsonObject.getString("result"),
                            object : AlertTask {
                                override fun doInPositiveClick(okBtn: String) {
                                    finish()
                                    Constants.setClipboard(applicationContext, jsonObject.toString())
                                }

                                override fun doInNegativeClick(cancle: String) {

                                }

                            })
                    }else{
                        Constants.showAlertDialog(this@CarBookingForm_ByRider,
                            "OK",
                            "",
                            "Car Booking Failed",
                            jsonObject.getString("result"),
                            object : AlertTask {
                                override fun doInPositiveClick(okBtn: String) {

                                }

                                override fun doInNegativeClick(cancle: String) {

                                }

                            })
                    }
                }
            }
            override fun successResponse(response: Response<Any>, requestCode: Int) {
                
            }
            override fun errorResponse(requestCode: Int, message: String) {
                ToastUtils.longToast(message)
            }
        })
    }
    fun selectDriverType(view: View) {
        Constants.showDropDownOfRelation(view, Constants.driveBy, this@CarBookingForm_ByRider)}
    fun hitRentNowButton(view: View) {
        if(validation()){
            val requestCall: Call<ResponseBody> =
                App.instance!!.apiInterface!!.hitcarBookingByRider(App!!.instance!!.mPrefs.getUserID(),
                    App!!.instance!!.mPrefs.getUserType(),id.toString(),
                    selectYouTV!!.text.toString(),"99","Success",
                    "Paid","Success",sourceLocationTV!!.text.toString(),
                    endLocationTV!!.text.toString(),PassengersED!!.text.toString(),
                    startDateTV!!.text.toString(),endDateTV!!.text.toString(),
                    startTimeTV!!.text.toString(),endTimeTV!!.text.toString())

            mApiCallHelperPresenter!!.hitApiCall(requestCall,123)
        }else{
            Constants.showAlertDialog(this,"OK","","Please Give All Valid Input","Error",
            object:AlertTask{
                override fun doInPositiveClick(okBtn: String) {

                }

                override fun doInNegativeClick(cancle: String) {

                }

            })
        }
    }
    private fun validation(): Boolean {
        if (startDateTV!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(startDateTV!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Booking Start Date",Constants.LONG)
            return false
        }
        if (startTimeTV!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(startTimeTV!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Booking Start Time",Constants.LONG)
            return false
        }
        if (endDateTV!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(endDateTV!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Booking End Date",Constants.LONG)
            return false
        }
        if (endTimeTV!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(endTimeTV!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Booking End Time",Constants.LONG)
            return false
        }
        if (selectYouTV!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(selectYouTV!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Drive By",Constants.LONG)
            return false
        }

        if (PassengersED!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(PassengersED!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Number Of Passengers",Constants.LONG)
            return false
        }
        if (sourceLocationTV!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(sourceLocationTV!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Source Location",Constants.LONG)
            return false
        }
        if (endLocationTV!!.text.trim().isNullOrEmpty()) {
            Animation.editText_Sh(endLocationTV!!)
            Utils.showToast(this@CarBookingForm_ByRider,"Please Enter Destination Location",Constants.LONG)
            return false
        }

        return true

    }
    fun finishActivity(view: View) {finish()}
    fun mSourceLocationClick(view: View) {
        if (hasGPSDevice(applicationContext)){
            var intent = Intent(this,PickLocationMaps::class.java)
            startActivityForResult(intent,265)
        }else{
            enableLoc()
        }

    }
    fun mEndLocationClick(view: View) {
        if (hasGPSDevice(applicationContext)){
            var intent = Intent(this,PickLocationMaps::class.java)
            startActivityForResult(intent,266)
        }else{
            enableLoc()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK){

            when(requestCode){
               265->{
                   sourceLocationTV!!.text = data!!.getStringExtra("LOCATION")
               }
               266->{
                   endLocationTV!!.text = data!!.getStringExtra("LOCATION")
               }

           }
        }else{
            ToastUtils.longToast("Request canceled")
        }
    }
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()
            }
        }
    }
    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }
    private fun hasGPSDevice(context: Context): Boolean {
        val mgr = context
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager ?: return false
        val providers = mgr.allProviders ?: return false
        return providers.contains(LocationManager.GPS_PROVIDER)
    }
    private fun enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(this@CarBookingForm_ByRider)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(bundle: Bundle?) {}
                    override fun onConnectionSuspended(i: Int) {
                        googleApiClient!!.connect()
                    }
                })
                .addOnConnectionFailedListener { connectionResult ->
                   
                }.build()
            googleApiClient!!.connect()
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = (30 * 1000).toLong()
            locationRequest.fastestInterval = (5 * 1000).toLong()
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)
            /*val result: PendingResult<LocationSettingsResult> =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            result.setResultCallback(object : ResultCallback<LocationSettingsResult?>(){
                fun onResult(result: LocationSettingsResult) {
                    val status: Status = result.status
                    when (status.getStatusCode()) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                this@Car_Booking_Form,
                                REQUEST_LOCATION
                            )
                            finish()
                        } catch (e: SendIntentException) {
                            // Ignore the error.
                        }
                    }
                }


            })*/
        }
    }
    fun openEndDatePicker(view: View) {datePicker!!.showTimeDatePicker(true,false,object:
        TimeDateCallBack {
        override fun onSuccess(DateTime: String?) {
            endDateTV!!.text=DateTime
        }

    })}
    fun openEndTimePicker(view: View) {
        datePicker!!.showTimeDatePicker(false,true,object: TimeDateCallBack {
            override fun onSuccess(DateTime: String?) {
                endTimeTV!!.text=DateTime
            }

        })
    }
    fun openStartTimePicker(view: View) {
        datePicker!!.showTimeDatePicker(false,true,object: TimeDateCallBack {
            override fun onSuccess(DateTime: String?) {
                startTimeTV!!.text=DateTime
            }

        })
    }
    fun openStartDatePicker(view: View) {datePicker!!.showTimeDatePicker(true,false,object:
        TimeDateCallBack {
        override fun onSuccess(DateTime: String?) {
            startDateTV!!.text=DateTime
        }

    })}
}