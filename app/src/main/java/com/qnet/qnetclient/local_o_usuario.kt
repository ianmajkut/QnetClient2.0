package com.qnet.qnetclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qnet.qnetclient.onboardingscreen_local.onboarding_screen_local
import com.qnet.qnetclient.onboardingscreen_usuario.onboarding_screen
import kotlinx.android.synthetic.main.activity_local_o_usuario.*

class local_o_usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_o_usuario)

        buttonLocal.setOnClickListener {
            val intent=Intent(this, onboarding_screen_local::class.java)
            startActivity(intent)
        }
        buttonUsuario.setOnClickListener {
            val intent=Intent(this, onboarding_screen::class.java)
            startActivity(intent)
        }
    }


}