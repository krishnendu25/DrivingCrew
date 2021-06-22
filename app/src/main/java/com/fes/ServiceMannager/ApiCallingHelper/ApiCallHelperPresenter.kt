package com.WingSure.ServiceMannager.ApiCallingHelper

import android.app.Activity
import android.content.Context
import android.util.Log
import com.fes.Utils.Loader.LocalModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by KRISHNENDU MANNA on 20,June,2021
 */
class ApiCallHelperPresenter(activity: Activity, apiResponseCallBack: ApiResponseCallBack) :
    HitTergetApiLink {
    var mContext: Context? = null
    var mActivity: Activity? = null
    var mClass: Any? = null
    var mApiResponseCallBack: ApiResponseCallBack? = null

    init {
        mActivity = activity
        mApiResponseCallBack = apiResponseCallBack
    }

    override fun hitApiCall(requestCall: Call<Any>, requestCode: Int, name: String) {
        LocalModel.instance!!.showProgressDialog(mActivity, "Loading")
        requestCall.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                LocalModel.instance!!.cancelProgressDialog()
                try {
                    if (response.body() != null) {
                        Log.e("@@@api",response.body().toString())
                        if (response.isSuccessful) {
                            if (responsCodeCheck(response.code())) {
                                try {
                                    mApiResponseCallBack!!.successResponse(response,requestCode)
                                } catch (e: Exception) {
                                    mApiResponseCallBack!!.errorResponse(requestCode,e.message.toString())
                                    LocalModel.instance!!.cancelProgressDialog()
                                }
                            } else {
                                mApiResponseCallBack!!.errorResponse(requestCode,"Invalid Response")
                                LocalModel.instance!!.cancelProgressDialog()
                            }
                        } else {
                            mApiResponseCallBack!!.errorResponse(requestCode,"Unsuccessful Response")
                            LocalModel.instance!!.cancelProgressDialog()
                        }
                    } else {
                        mApiResponseCallBack!!.errorResponse(requestCode,"Invalid Response")
                        LocalModel.instance!!.cancelProgressDialog()
                    }
                } catch (e: Exception) {
                    mApiResponseCallBack!!.errorResponse(requestCode,e.message.toString())
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                mApiResponseCallBack!!.errorResponse(requestCode,t.message.toString())
            }
        })
    }


    override fun hitApiCall(requestCall: Call<ResponseBody>, requestCode: Int) {
        LocalModel.instance!!.showProgressDialog(mActivity, "Loading")
        requestCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                LocalModel.instance!!.cancelProgressDialog()
                try {
                    if (response.body() != null) {
                        Log.e("@@@api",response.body().toString())
                        if (response.isSuccessful) {
                            if (responsCodeCheck(response.code())) {
                                mApiResponseCallBack!!.successResponse(response.body()!!,requestCode)
                                try {
                                    var jsonObject = JSONObject(response.body()!!.string())
                                    mApiResponseCallBack!!.successResponse(jsonObject,requestCode)
                                } catch (e: Exception) {
                                    mApiResponseCallBack!!.errorResponse(requestCode,e.message.toString())
                                }
                            } else {
                                mApiResponseCallBack!!.errorResponse(requestCode,"Invalid Response")
                                LocalModel.instance!!.cancelProgressDialog()
                            }
                        } else {
                            mApiResponseCallBack!!.errorResponse(requestCode,"Unsuccessful Response")
                            LocalModel.instance!!.cancelProgressDialog()
                        }
                    } else {
                        mApiResponseCallBack!!.errorResponse(requestCode,"Invalid Response")
                        LocalModel.instance!!.cancelProgressDialog()
                    }
                } catch (e: Exception) {
                    mApiResponseCallBack!!.errorResponse(requestCode,e.message.toString())
                    LocalModel.instance!!.cancelProgressDialog()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                LocalModel.instance!!.cancelProgressDialog()
                mApiResponseCallBack!!.errorResponse(requestCode,t.message.toString())
            }
        })


    }


    fun responsCodeCheck(code: Int): Boolean {
        if (code == 200) {
            return true
        }
        return false
    }


}