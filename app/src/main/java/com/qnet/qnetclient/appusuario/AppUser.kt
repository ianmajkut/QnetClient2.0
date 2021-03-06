package com.qnet.qnetclient.appusuario

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.qnet.qnetclient.R

class AppUser : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast
    //private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_user)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_map,
                R.id.navigation_fila,
                R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp()
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment),
            null
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

//    override fun onBackPressed() {
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
//            val intent = Intent(this, AppUser::class.java)
//            startActivity(intent)
//            backToast.cancel()
//            super.onBackPressed()
//            moveTaskToBack(true)
//            finish()
//            return
//        } else {
//            backToast = Toast.makeText(
//                baseContext,
//                "Presione nuevamente \"Atrás\" para salir",
//                Toast.LENGTH_SHORT
//            )
//            backToast.show()
//        }
//        backPressedTime = System.currentTimeMillis()
//    }
}