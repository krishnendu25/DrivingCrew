package com.fes.View.UI.Activity.Others

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.WingSure.ServiceMannager.ApiCallingHelper.ApiCallHelperPresenter
import com.WingSure.ServiceMannager.ApiCallingHelper.ApiResponseCallBack
import com.fes.App
import com.fes.Model.ReponseModel.AboutUs
import com.fes.Model.ReponseModel.BookedCarListing
import com.fes.R
import com.fes.Utils.ToastUtils
import com.fes.View.Adapter.MyBookingAdapter
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class AboutUs : AppCompatActivity() {
    var mApiCallHelperPresenter: ApiCallHelperPresenter? = null
    var img: ImageView? = null
    var AboutUsTV: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        iniView()
        hitAboutUs()
    }

    private fun hitAboutUs() {
        val requestCall: Call<AboutUs> =
            App.instance!!.apiInterface!!.hitaboutUs()
        mApiCallHelperPresenter!!.hitApiCall(requestCall as Call<Any>,123,"")
    }

    private fun iniView() {
        img =  findViewById(R.id.img);
        AboutUsTV =  findViewById(R.id.AboutUsTV);
        mApiCallHelperPresenter = ApiCallHelperPresenter(this,object: ApiResponseCallBack {
            override fun successResponse(result: ResponseBody, requestCode: Int) {

            }
            override fun successResponse(jsonObject: JSONObject, requestCode: Int) {

            }
            override fun successResponse(response: Response<Any>, requestCode: Int) {
                if (requestCode==123){
                    val requestCall = response as Response<AboutUs>
                    if (requestCall.body()!!.result.equals("success")){
                        AboutUsTV!!.setText(requestCall.body()!!.aboutDetails)
                    }
                }
            }
            override fun errorResponse(requestCode: Int, message: String) {
                ToastUtils.longToast(message)
            }
        })
    }
}