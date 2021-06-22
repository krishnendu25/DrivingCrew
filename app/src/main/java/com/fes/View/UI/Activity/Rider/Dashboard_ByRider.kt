package com.fes.View.UI.Activity.Rider

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.WingSure.ServiceMannager.ApiCallingHelper.ApiCallHelperPresenter
import com.WingSure.ServiceMannager.ApiCallingHelper.ApiResponseCallBack
import com.fes.App
import com.fes.BuildConfig
import com.fes.Constant.Constants
import com.fes.Model.ReponseModel.*
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.fes.Utils.ToastUtils
import com.fes.Utils.Utils
import com.fes.View.Adapter.CarListAdpter
import com.fes.View.Adapter.DriverBookedAdpter
import com.fes.View.Adapter.MyBookingAdapter
import com.fes.View.Interface.AlertTask
import com.fes.View.UI.Activity.Others.AboutUs
import com.fes.View.UI.Activity.Others.LoginActivity
import com.fes.View.UI.Activity.Others.ProfileActivity
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Dashboard_ByRider : AppCompatActivity(),View.OnClickListener {
    var driveProfileIV: CircleImageView? = null
    var nameTV: TextView? = null
    var editProfileTV: TextView? = null
    var MyRideTV: TextView? = null
    var ReferDrivingCrewTV: TextView? = null
    var SupportTV: TextView? = null
    var AboutUsTV: TextView? = null
    var LogoutTV: TextView? = null
    var mApiCallHelperPresenter: ApiCallHelperPresenter? = null
    var NavigationView: NavigationView? = null
    var drawerLayoutChat: DrawerLayout? = null
    var searchEdittext: EditText? = null
    var viewAllCarList: TextView? = null
    var carBookedList: List<CarBooked> = ArrayList()
    var carRecyclerView: RecyclerView? = null
    var viewAllBookingList: TextView? = null
    var titleTV: TextView? = null
    var bookingRecyclerView: RecyclerView? = null
    var bookingDriverRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        iniView()
        iniMenuSetup()
        hitFetchAlltheCar()
    }
    private fun hitFetchAlltheCar() {

        LocalModel.instance!!.showProgressDialog(this@Dashboard_ByRider, "Loading..")
        val requestCall: Call<AllCarList_api> =
            App.instance!!.apiInterface!!.AllcarListingApi(App!!.instance!!.mPrefs.getUserID(),App!!.instance!!.mPrefs.getUserType())
        requestCall.enqueue(object : Callback<AllCarList_api> {
            override fun onResponse(
                call: Call<AllCarList_api>,
                response: Response<AllCarList_api>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {
                   if (response!!.body()!!.result.equals("success")){
                       setUpCarListAdapter(response!!.body()!!.carList,true)
                    }
                } else {
                    Utils.showToast(applicationContext,resources.getString(R.string.null_Response),
                        Constants.MIDDLE_LONG)
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }
            override fun onFailure(call: Call<AllCarList_api>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),
                    Constants.MIDDLE_LONG)
            }
        })
        
        
    }
    private fun iniMenuSetup() {
        driveProfileIV =  findViewById(R.id.driveProfileIV);
        nameTV =  findViewById(R.id.nameTV);
        editProfileTV =  findViewById(R.id.editProfileTV);
        MyRideTV =  findViewById(R.id.MyRideTV);
        ReferDrivingCrewTV =  findViewById(R.id.ReferDrivingCrewTV);
        SupportTV =  findViewById(R.id.SupportTV);
        AboutUsTV =  findViewById(R.id.AboutUsTV);
        LogoutTV =  findViewById(R.id.LogoutTV);
        editProfileTV!!.setOnClickListener({
            goToActivity(applicationContext, ProfileActivity::class.java)
        })
        LogoutTV!!.setOnClickListener({
            goToActivity(applicationContext, LoginActivity::class.java)
            App.instance.mPrefs.setUserID("")
        })
        SupportTV!!.setOnClickListener({
            Constants.showAlertDialog(this,
                "CALL US",
               "EMAIL US","How can we help you?","Contact Us",object : AlertTask {
                override fun doInPositiveClick(okBtn: String) {
                    try {
                        val dialIntent = Intent(Intent.ACTION_DIAL)
                        dialIntent.data = Uri.parse("tel:" + "08888888888")
                        startActivity(dialIntent)
                    } catch (e: Exception) {
                    }
                }
                override fun doInNegativeClick(cancle: String) {
                    try {
                        composeEmail("support@DrivingCrew.com","How can we help you?")
                    } catch (e: Exception) {
                    }

                }
            })
        })
        AboutUsTV!!.setOnClickListener({
            goToActivity(applicationContext, AboutUs::class.java)
        })
        ReferDrivingCrewTV!!.setOnClickListener({
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage =
                    """
        ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
        """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        })
    }
    private fun iniView() {
        titleTV =  findViewById(R.id.titleTV);
        titleTV!!.text = "Welcome To Driving Crew"
        NavigationView =  findViewById(R.id.NavigationView);
        drawerLayoutChat =  findViewById(R.id.drawerLayoutChat);
        searchEdittext =  findViewById(R.id.searchEdittext);
        viewAllCarList =  findViewById(R.id.viewAllCarList);
        carRecyclerView = findViewById(R.id.carRecyclerView);
        viewAllBookingList =  findViewById(R.id.viewAllBookingList);
        bookingRecyclerView =  findViewById(R.id.bookingRecyclerView);
        bookingDriverRecyclerView =  findViewById(R.id.bookingDriverRecyclerView);
        Utils.setLayoutManager(carRecyclerView!!,true,false)
        Utils.setLayoutManager(bookingRecyclerView!!,false,true)
        Utils.setLayoutManager(bookingDriverRecyclerView!!,false,true)
        mApiCallHelperPresenter = ApiCallHelperPresenter(this,object: ApiResponseCallBack {
            override fun successResponse(result: ResponseBody, requestCode: Int) {

            }
            override fun successResponse(jsonObject: JSONObject, requestCode: Int) {

            }
            override fun successResponse(response: Response<Any>, requestCode: Int) {
                if (requestCode==123){
                    val requestCall = response as Response<BookedCarListing>
                    if (requestCall.body()!!.result.equals("success")){
                        carBookedList= ArrayList()
                        carBookedList=requestCall.body()!!.carBookedList
                        var mAdapter = MyBookingAdapter(carBookedList!!,this@Dashboard_ByRider)
                        bookingRecyclerView!!.adapter = mAdapter

                    }
                }
            }
            override fun errorResponse(requestCode: Int, message: String) {
                ToastUtils.longToast(message)
            }
        })
    }
    fun openMenuClick(view: View) {
        drawerLayoutChat!!.openDrawer(GravityCompat.START);}
    override fun onClick(v: View?) {

    }
    fun setUpCarListAdapter(carList: List<Car>, b: Boolean) {
        var ada = CarListAdpter(carList,this@Dashboard_ByRider)
        carRecyclerView!!.adapter=ada
    }
    fun setUpMyDetails(){
        /* driveProfileIV
         nameTV*/
    }
    fun goToActivity(mContext: Context?, cls: Class<*>?) {
        val intent = Intent(mContext, cls)
        startActivity(intent)
    }
    fun hireAdriver(view: View) {
        goToActivity(applicationContext, HireADriver_ByRider::class.java)
    }
    
    private fun hitFetchMyBooking(userID: String) {
        val requestCall: Call<BookedCarListing> =
            App.instance!!.apiInterface!!.CarBookngListing(App!!.instance!!.mPrefs.getUserID())
        mApiCallHelperPresenter!!.hitApiCall(requestCall as Call<Any>,123,"")
    }
    fun composeEmail(addresses:String, subject: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        hitFetchMyBooking(App.instance.mPrefs.getUserID())
        hitFetchMyDriving(App.instance.mPrefs.getUserID())
        hitUserDetails()
    }

    private fun hitFetchMyDriving(userID: String) {
        val requestCall: Call<DriverBookedListAPI> =
            App.instance!!.apiInterface!!.getdriverBookngListing(userID)
        requestCall.enqueue(object : Callback<DriverBookedListAPI> {
            override fun onResponse(
                call: Call<DriverBookedListAPI>,
                response: Response<DriverBookedListAPI>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {
                    if (response!!.body()!!.result.equals("success")){
                        setUpDriverAdapter(response!!.body()!!.driverBookedList,true)
                    }
                } else {
                    Utils.showToast(applicationContext,resources.getString(R.string.null_Response),
                        Constants.MIDDLE_LONG)
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }
            override fun onFailure(call: Call<DriverBookedListAPI>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),
                    Constants.MIDDLE_LONG)
            }
        })
    }

    private fun setUpDriverAdapter(driverBookedList: List<DriverBooked>, b: Boolean) {
        var ada = DriverBookedAdpter(driverBookedList!!,this@Dashboard_ByRider)
        bookingDriverRecyclerView!!.adapter=ada
    }

    fun hitUserDetails(){
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
            nameTV!!.setText(body!!.Name)
        try {
            if (body.ProfilePhoto.contains("http"))
                Picasso.get()
                    .load(body.ProfilePhoto)
                    .into(driveProfileIV);
        } catch (e: Exception) {
        }

    }
}