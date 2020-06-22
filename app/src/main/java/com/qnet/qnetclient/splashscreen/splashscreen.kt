package com.qnet.qnetclient.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.navigation.findNavController
import com.qnet.qnetclient.R
import com.qnet.qnetclient.onboardingscreen.onboarding_screen
import com.google.firebase.auth.FirebaseAuth

class splashscreen : AppCompatActivity() {

lateinit var handler: Handler
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen)


        handler = Handler()

        handler.postDelayed({
            val intent =Intent(this, onboarding_screen::class.java)
            startActivity(intent)
            finish()

        }, 2000)



    }
}
