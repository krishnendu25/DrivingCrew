package com.WingSure.ServiceMannager.ApiCallingHelper

import okhttp3.ResponseBody
import retrofit2.Call

/**
 * Created by KRISHNENDU MANNA on 20,June,2021
 */
interface HitTergetApiLink {
    fun hitApiCall(requestCall: Call<Any>,requestCode:Int,name:String)
    fun hitApiCall(requestCall: Call<ResponseBody>,requestCode:Int)
}