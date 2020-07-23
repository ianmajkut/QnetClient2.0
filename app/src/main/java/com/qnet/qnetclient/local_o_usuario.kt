package com.qnet.qnetclient

import android.content.Intent
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
            backToast = makeText(baseContext, "Presione nuevamente 'Atras' para salir", LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}