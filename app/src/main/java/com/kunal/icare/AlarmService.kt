package com.kunal.icare

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.DocumentChange.Type.MODIFIED
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AlarmService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0)
        val notification = NotificationCompat.Builder(this,"Alarm")
                .setContentTitle("Test")
                .setContentText("Test")
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1,notification)
        val id = intent!!.getStringExtra("id")
        Firebase.firestore.collection("patients").addSnapshotListener{snapshot,e ->

            for (dc in snapshot!!.documentChanges) {
                if (dc.document.id==id && dc.type == MODIFIED) {
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        //deprecated in API 26
                        vibrator.vibrate(5000)
                    }
                }
            }
        }
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "Alarm",
                "Foreground Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService((NotificationManager::class.java))
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


}