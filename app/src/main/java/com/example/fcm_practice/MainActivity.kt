package com.example.fcm_practice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MainActivity : AppCompatActivity() {
    lateinit var context : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = applicationContext

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                var token = task.result
                Log.d("Token Cheack",token)
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener {
            task ->
            if (!task.isSuccessful){
                Log.d("bad","Fail")
            }else{
                Log.d("good","Complete")
            }
        }
    }
}