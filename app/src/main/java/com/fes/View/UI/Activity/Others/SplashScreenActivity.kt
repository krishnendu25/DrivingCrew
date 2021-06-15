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
import com.fes.R
import com.fes.Utils.StringUtils

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
            /*if (mPrefs.getLoginStatus()) {
                goToActivity(mContext, DashboardActivity::class.java)
                finish()
            } else {
                goToActivity(mContext, LoginActivity::class.java)
                finish()
            }*/
            goToActivity(mContext, LoginActivity::class.java)
            finish()
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