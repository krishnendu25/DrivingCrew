package com.fes

import android.app.Application
import android.content.Context
import android.os.Process
import android.util.Log
import androidx.multidex.MultiDex
import com.fes.Utils.Prefs
import com.fes.Utils.StringUtils
import com.fes.hoori.controller.ApiClient
import com.fes.hoori.controller.ApiInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.jaredrummler.cyanea.Cyanea
import java.io.IOException


class App : Application() {
    public var tokenFCM : String ? =null
    public var apiInterface: ApiInterface? = null
   lateinit var mPrefs: Prefs
    public var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    override fun onCreate() {
        super.onCreate()
        Cyanea.init(this, resources)
        initApplication()
        val pid = Process.myPid()
        val whiteList = "logcat -P '$pid'"
        try {
            Runtime.getRuntime().exec(whiteList).waitFor()
        } catch (e: IOException) {
        } catch (e: Exception) {
        }
        apiInterface = ApiClient.getRetrofit()!!.create(ApiInterface::class.java)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            tokenFCM = task.result
         //   Log.e("@@@@@FCM",tokenFCM)
            mPrefs!!.setString(StringUtils.obj.tokenFCM,tokenFCM!!);
        })
    }

    private fun initApplication() {
        instance = this
        mPrefs= Prefs(instance!!)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    fun getInstance(): App? {
        return instance
    }
    companion object {

       lateinit var instance: App
            private set

    }
}