package com.fes.View.UI.Activity.Driver

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.fes.App
import com.fes.BuildConfig
import com.fes.Constant.Constants
import com.fes.Model.ReponseModel.DriverBookingShowDriver
import com.fes.Model.ReponseModel.GetCarBookingDriver
import com.fes.Model.ReponseModel.UserDetails_Api
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.fes.Utils.Utils
import com.fes.View.Interface.AlertTask
import com.fes.View.UI.Activity.Others.AboutUs
import com.fes.View.UI.Activity.Others.LoginActivity
import com.fes.View.UI.Activity.Others.ProfileActivity
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Driver_DashBoard : AppCompatActivity(), View.OnClickListener {
    var drawerLayoutChat: DrawerLayout? = null
    var searchMyRide: EditText? = null
    var diverRecyclerView: RecyclerView? = null
    var NavigationView: NavigationView? = null
    var titleTV: TextView? = null
    var driveProfileIV: CircleImageView? = null
    var nameTV: TextView? = null
    var editProfileTV: TextView? = null
    var MyRideTV: TextView? = null
    var ReferDrivingCrewTV: TextView? = null
    var SupportTV: TextView? = null
    var AboutUsTV: TextView? = null
    var LogoutTV: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver__dash_board)
        iniView()
        iniMenuSetup()
    }
    private fun iniMenuSetup() {
        driveProfileIV = NavigationView!!.findViewById(R.id.driveProfileIV);
        nameTV = NavigationView!!.findViewById(R.id.nameTV);
        editProfileTV = NavigationView!!.findViewById(R.id.editProfileTV);
        MyRideTV = NavigationView!!.findViewById(R.id.MyRideTV);
        ReferDrivingCrewTV = NavigationView!!.findViewById(R.id.ReferDrivingCrewTV);
        SupportTV = NavigationView!!.findViewById(R.id.SupportTV);
        AboutUsTV = NavigationView!!.findViewById(R.id.AboutUsTV);
        LogoutTV = NavigationView!!.findViewById(R.id.LogoutTV);
        titleTV =  findViewById(R.id.titleTV);
        titleTV!!.text = "Welcome To Driving Crew"
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
        drawerLayoutChat = findViewById(R.id.drawerLayoutChat);
        searchMyRide = findViewById(R.id.searchMyRide);
        diverRecyclerView = findViewById(R.id.diverRecyclerView);
        NavigationView = findViewById(R.id.NavigationView);
        Utils.setLayoutManager(diverRecyclerView!!, false, true)
    }
    fun openMenuClickk(view: View) {
        drawerLayoutChat!!.openDrawer(GravityCompat.START);}
    override fun onClick(v: View?) {

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
    fun goToActivity(mContext: Context?, cls: Class<*>?) {
        val intent = Intent(mContext, cls)
        startActivity(intent)
    }
    override fun onResume() {
        super.onResume()
        hitUserDetails()
        hitGetCarBookingGet(App.instance.mPrefs.getUserID())
        hitDriverBookingGet(App.instance.mPrefs.getUserID())
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

    
    private fun hitGetCarBookingGet(userID: String) {
        val requestCall: Call<GetCarBookingDriver> =
            App.instance!!.apiInterface!!.DriverCarBookedList(userID)
        requestCall.enqueue(object : Callback<GetCarBookingDriver> {
            override fun onResponse(
                call: Call<GetCarBookingDriver>,
                response: Response<GetCarBookingDriver>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {



                } else {
                    Utils.showToast(applicationContext,resources.getString(R.string.null_Response),
                        Constants.MIDDLE_LONG)
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }
            override fun onFailure(call: Call<GetCarBookingDriver>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),
                    Constants.MIDDLE_LONG)
            }
        })
    }
    private fun hitDriverBookingGet(userID: String) {
        val requestCall: Call<DriverBookingShowDriver> =
            App.instance!!.apiInterface!!.tDriverBookedList(userID)
        requestCall.enqueue(object : Callback<DriverBookingShowDriver> {
            override fun onResponse(
                call: Call<DriverBookingShowDriver>,
                response: Response<DriverBookingShowDriver>
            ) {
                LocalModel.instance!!.cancelProgressDialog()
                if (response.body() != null) {



                } else {
                    Utils.showToast(applicationContext,resources.getString(R.string.null_Response),
                        Constants.MIDDLE_LONG)
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }
            override fun onFailure(call: Call<DriverBookingShowDriver>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                Utils.showToast(applicationContext,resources.getString(R.string.SomethingLater),
                    Constants.MIDDLE_LONG)
            }
        })
    }
}