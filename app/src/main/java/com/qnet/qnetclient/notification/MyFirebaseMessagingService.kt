package com.qnet.qnetclient.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color.argb
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ian.bottomnavigation.ui.fila.FilaFragment
import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.AppUser
import com.qnet.qnetclient.local_o_usuario

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//
//        Looper.prepare()
//        Handler().post {
//            val data: Map<String, String> = remoteMessage.data
//            val body: String? = data["body"]
//            Toast.makeText(baseContext, body, Toast.LENGTH_LONG).show()
//        }
//        Looper.loop()
        Log.d("notifications", "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("notification", "Message data payload: ${remoteMessage.data}")
            scheduleJob()
        }
        remoteMessage.notification?.let {
            Log.d("notifications", "Message Notification Body: ${it.body}")
        }

        remoteMessage.notification?.let { sendNotification(it) }
    }

    override fun onNewToken(token: String) {
        Log.d("notifications", "Refreshed token: $token")
    }

    private fun scheduleJob() {
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(application).beginWith(work).enqueue()
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {
        val intent = Intent(this, FilaFragment::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher)
            .setLights(argb(200, 0, 0, 255), 1000, 1000)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
            "Notificaciones de Qnet",
            NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}