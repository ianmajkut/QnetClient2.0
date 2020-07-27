package com.qnet.qnetclient.applocal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.qnet.qnetclient.R
import com.qnet.qnetclient.local_o_usuario

class AppLocal : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_local)
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            val intent = Intent(this, AppLocal::class.java)
            startActivity(intent)
            backToast.cancel()
            super.onBackPressed()
            moveTaskToBack(true)
            finish()
            return
        } else {
            backToast = Toast.makeText(
                baseContext,
                "Presione nuevamente 'Atras' para salir",
                Toast.LENGTH_SHORT
            )
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}