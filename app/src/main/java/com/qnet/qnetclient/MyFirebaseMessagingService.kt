package com.qnet.qnetclient

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("notification", "onMessageReceived: called")
        Log.d("notification", "onMessageReceived: Message received from: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("notification", "onMessageReceived: Data: ${remoteMessage.data}")
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Log.d("notification", "onDeletedMessages called")
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("notification", "onNewToken called")
    }
}