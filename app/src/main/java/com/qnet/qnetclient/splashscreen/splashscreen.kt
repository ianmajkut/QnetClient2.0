package com.qnet.qnetclient.splashscreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseApp
import com.qnet.qnetclient.R
import com.qnet.qnetclient.applocal.AppLocal
import com.qnet.qnetclient.appusuario.AppUser
import com.qnet.qnetclient.local_o_usuario
import com.qnet.qnetclient.onboardingscreen_usuario.onboarding_screen
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlin.properties.Delegates


class splashscreen : AppCompatActivity() {

    lateinit var handler: Handler
    private val PERMISSION_ID = 1000
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var viewModel: FirestoreViewModel
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
        viewModel = FirestoreViewModel()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        val preferences: SharedPreferences =
            this.getSharedPreferences("RememberMe", Context.MODE_PRIVATE)
        val checkbox: Boolean = preferences.getBoolean("remember", false)
        val name: String? = preferences.getString("email", "")
        val password: String? = preferences.getString("password", "")

        if (checkbox) {
            getLocation()
            viewModel.singInUser(name!!,password!!).observeForever{
                when(it){
                    0 -> {val intent =Intent(this, local_o_usuario::class.java)
                        startActivity(intent) }
                    1 -> {observer2()}
                    2 -> {val intent =Intent(this, AppLocal::class.java)
                        startActivity(intent) }
                }
            }
        } else {
            handler = Handler()

            handler.postDelayed({
                val intent = Intent(this, local_o_usuario::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
               this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful) {
                latitude = it.result?.latitude!!
                longitude = it.result?.longitude!!
            }
        }
    }

    private fun observer2() {
        viewModel.updateUbicacion(latitude, longitude,true).observeForever {
            if (it) {
                observer3()
            } else {
                val intent = Intent(this, local_o_usuario::class.java)
                startActivity(intent)
            }
        }
    }

    private fun observer3() {
        viewModel.localesCercanos().observeForever {
            if (it) {
                viewModel.refreshToken()
                val intent = Intent(this, AppUser::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, local_o_usuario::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Location", "Permission granted")
            }
        }
    }
}
