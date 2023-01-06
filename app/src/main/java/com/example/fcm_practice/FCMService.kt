package com.example.fcm_practice

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.data.size > 0) {
            sendNotification(message.data.get("title")!!, message.data.get("body")!!)
        }
        if (message.notification != null) {
            sendNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("Token Check", "onNewToken : $token")
        super.onNewToken(token)
    }

    fun sendNotification(title: String, message: String) {

        val intent = Intent(this, MainActivity::class.java)
        val channelId = "channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(defaultUri)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel =
                NotificationChannel(channelId, "what", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.setSound(defaultUri, null)
            notificationManager.createNotificationChannel(notificationChannel)

        }
        notificationManager.notify(0, builder.build())
    }
}