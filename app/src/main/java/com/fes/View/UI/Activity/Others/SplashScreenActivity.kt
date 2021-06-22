package com.fes.View.UI.Activity.Others

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.fes.App
import com.fes.Constant.Constants.obj.userTypeList
import com.fes.R
import com.fes.Utils.StringUtils
import com.fes.View.UI.Activity.Driver.Driver_DashBoard
import com.fes.View.UI.Activity.Rider.Dashboard_ByRider

class SplashScreenActivity : AppCompatActivity() {
    private var mContext: Context? = null
    private var splashLogo: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        fullScreenPortrait()
        instantiation()

        //Animation Logo
        splashLogo!!.setVisibility(View.VISIBLE)
        val animSlide = AnimationUtils.loadAnimation(
            applicationContext, R.anim.zoom_out
        )
        splashLogo!!.startAnimation(animSlide)
        val handler = Handler()
        val runnable = Runnable {
            if ( App!!.instance!!.mPrefs!!.getUserID().toString().isNullOrBlank()) {
                goToActivity(mContext, LoginActivity::class.java)
                finish()
            } else {
                if (App!!.instance!!.mPrefs!!.getUserType().equals(userTypeList[0])){
                    startActivity(Intent(applicationContext, Driver_DashBoard::class.java))
                    finish()
                }
                if (App!!.instance!!.mPrefs!!.getUserType().equals(userTypeList[1])){

                 startActivity(Intent(applicationContext, Dashboard_ByRider::class.java))
                    finish()
                }
            }

        }
        handler.postDelayed(runnable, StringUtils.SPLASH_TIMEOUT)
    }

    private fun instantiation() {
        mContext = applicationContext
        splashLogo = findViewById(R.id.splashLogo)
    }

    fun goToActivity(mContext: Context?, cls: Class<*>?) {
        val intent = Intent(mContext, cls)
        startActivity(intent)
    }

    fun fullScreenPortrait() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}