package com.fes.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fes.R
import com.fes.View.UI.Activity.Others.LoginActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

/**
 * Created by Krishnendu on 2020.
 */
class MyFirebaseMessegingService : FirebaseMessagingService() {
    var TAG = "ANUJ"
    private var mPrefs: Prefs? = null
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage != null) {
            if (remoteMessage.data.size > 0) {
                val map: Map<*, *> = remoteMessage.data
                if(map["title"].toString().equals("null")||map["body"].toString().equals("null")){
                    val title =map["title"].toString()
                    val notificationBody = map["body"].toString()
                    sendNotification(notificationBody, title)
                }else{
                    var json = JSONObject(JSONObject(map).getString("data"))
                    Log.d(javaClass.simpleName, map["title"].toString())
                    Log.d(javaClass.simpleName, map["body"].toString())
                    val title =json.getString("title").toString()
                    val notificationBody = json.getString("body").toString()
                    sendNotification(notificationBody, title)
                }



                val channelId = getString(R.string.app_name)

            } else {
                sendNotification(
                    remoteMessage.notification!!.body, remoteMessage.notification!!.title
                )
            }
            if (remoteMessage.notification != null) {
                sendNotification(
                    remoteMessage.notification!!.body, remoteMessage.notification!!.title
                )
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        mPrefs!!.setString(StringUtils.obj.tokenFCM,token);
    }

    private fun sendNotification(messageBody: String?, title: String?) {
        mPrefs = Prefs(this@MyFirebaseMessegingService)
        var intent: Intent? = null
        if (mPrefs!!.getBoolean(StringUtils.loginStatus, false)) {
            intent = Intent(this, LoginActivity::class.java)
        } else {   intent = Intent(this, LoginActivity::class.java)
        }
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = getString(R.string.app_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources, R.mipmap.ic_launcher
                )
            )
            .setContentTitle(title)
            .setColor(Color.parseColor("#4CAF50"))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(Notification.PRIORITY_MAX)
            .setWhen(0)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        var refreshedToken: String? = null
    }
}