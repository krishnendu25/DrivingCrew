package com.WingSure.ServiceMannager.ApiCallingHelper

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.security.MessageDigest

/**
 * Created by KRISHNENDU MANNA on 20,June,2021
 */
interface ApiResponseCallBack {

    fun successResponse(result: ResponseBody,requestCode:Int)
    fun successResponse(jsonObject: JSONObject,requestCode:Int)
    fun successResponse(response: Response<Any>, requestCode:Int)
    fun errorResponse(requestCode:Int,message:String)
}