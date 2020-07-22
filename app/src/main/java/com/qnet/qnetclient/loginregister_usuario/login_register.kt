package com.qnet.qnetclient.loginregister_usuario

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlin.properties.Delegates


class login_register : Fragment() {
    private val PERMISSION_ID = 1000
    private var loadingDialog: Dialog? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()
    private lateinit var viewModel: FirestoreViewModel
    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        viewModel = FirestoreViewModel()
        getLocation()
        buttonNew.setOnClickListener {
            findNavController().navigate(R.id.next_action)
        }
        buttonForget.setOnClickListener {
            findNavController().navigate(R.id.forget_action)
        }
        buttonNext.setOnClickListener {
            login()
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(
                    "Location",
                    "Latitude: " + it.result?.latitude + ", Longitude: " + it.result?.longitude
                )
                latitude = it.result?.latitude!!
                longitude = it.result?.longitude!!
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

    private fun login() {
        val name = edtxt_eMail.text.toString().trim()
        val password = edtxt_Password.text.toString().trim()

        if (name.isNotEmpty() && password.isNotEmpty()) {
            //@Ian falta poner un progress bar para ver el progreso
            showLoading()
            obsever(name, password)
        } else {
            Toast.makeText(activity, "Error Campos Incompletos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(requireContext())
    }

    private fun obsever(name:String,password:String) {
        viewModel.singInUser(name,password).observeForever{
            if(it) {
//                Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
                observer2()
            } else {
                Toast.makeText(activity, "Usuario no Registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observer2() {
        viewModel.updateUbicacion(latitude, longitude).observeForever {
            if (it) {
                observer3()
            }
        }
    }

    private fun observer3() {
        viewModel.localesCercanos().observeForever {
            if (it) {
                hideLoading()
                findNavController().navigate(R.id.menu_principal_action)
            }
        }
    }
}