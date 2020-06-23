package com.qnet.qnetclient.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.qnet.qnetclient.R
import com.qnet.qnetclient.local_o_usuario
import com.qnet.qnetclient.onboardingscreen_usuario.onboarding_screen

class splashscreen : AppCompatActivity() {

lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen)

        handler = Handler()

        handler.postDelayed({
            val intent =Intent(this, local_o_usuario::class.java)
            startActivity(intent)
            finish()

        }, 2000)



    }
}
