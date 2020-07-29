package com.qnet.qnetclient

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.*
import com.qnet.qnetclient.onboardingscreen_local.onboarding_screen_local
import com.qnet.qnetclient.onboardingscreen_usuario.onboarding_screen
import kotlinx.android.synthetic.main.activity_app_user.*
import kotlinx.android.synthetic.main.activity_local_o_usuario.*
import kotlin.properties.Delegates

class local_o_usuario : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_o_usuario)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_DEFAULT))
        }

        buttonLocal.setOnClickListener {
            val intent=Intent(this, onboarding_screen_local::class.java)
            startActivity(intent)
        }
        buttonUsuario.setOnClickListener {
            val intent=Intent(this, onboarding_screen::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            val intent = Intent(this, local_o_usuario::class.java)
            startActivity(intent)
            backToast.cancel()
            super.onBackPressed()
            moveTaskToBack(true)
            finish()
            return
        } else {
            backToast = makeText(baseContext, "Presione nuevamente \"Atrás\" para salir", LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}