package com.qnet.qnetclient.loginregister_usuario

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlin.properties.Delegates

class login_register : Fragment() {

    private val LOCATION_PERMISSION_ID = 1000
    private var loadingDialog: Dialog? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()
    private lateinit var viewModel: FirestoreViewModel
    private var rememberMe: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        viewModel = FirestoreViewModel()
        getLocation()

        val preferences: SharedPreferences =
            requireActivity().getSharedPreferences("RememberMe", Context.MODE_PRIVATE)
        val checkbox: Boolean = preferences.getBoolean("remember", false)
        val name: String? = preferences.getString("name", "")
        val password: String? = preferences.getString("password", "")
        if (checkbox) {
            login(name!!, password!!)
        } else {
            Toast.makeText(requireContext(), "Please Log In", Toast.LENGTH_SHORT).show()
        }

        buttonNew.setOnClickListener {
            findNavController().navigate(R.id.next_action)
        }
        buttonForget.setOnClickListener {
            findNavController().navigate(R.id.forget_action)
        }
        buttonNext.setOnClickListener {
            getData()
        }

        checkboxRecordar.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isChecked) {
                rememberMe = true
            } else if (!buttonView.isChecked) {
                rememberMe = false
            }
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
                    android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_ID)
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
        if (requestCode == LOCATION_PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Location", "Permission granted")
            }
        }
    }

    private fun getData() {
        val name = edtxt_eMail.text.toString().trim()
        val password = edtxt_Password.text.toString().trim()

        login(name, password)
    }

    private fun login(name: String, password: String) {
        if (name.isNotEmpty() && password.isNotEmpty()) {
            //@Ian falta poner un progress bar para ver el progreso
            if (rememberMe) {
                val preferences: SharedPreferences =
                    requireActivity().getSharedPreferences(
                        "RememberMe",
                        Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putBoolean("remember", true)
                editor.putString("name", name)
                editor.putString("password", password)
                editor.apply()
            } else {
                val preferences: SharedPreferences =
                    requireActivity().getSharedPreferences(
                        "RememberMe",
                        Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putBoolean("remember", false)
                editor.putString("name", null)
                editor.putString("password", null)
                editor.apply()
            }
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
            if(it!=null) {
                when (it) {
                    0 -> {
                        Toast.makeText(activity, "Usuario no Registrado", Toast.LENGTH_SHORT).show()
                        hideLoading()
                    }
                    1 -> {
                        observer2()
                    }
                    2 -> {
                        Toast.makeText(activity, "El Usuario es un Local", Toast.LENGTH_SHORT)
                            .show()
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun observer2() {
        viewModel.updateUbicacion(latitude, longitude,true).observeForever {
            if (it) {
                observer3()
            }else{
                hideLoading()
            }
        }
    }

    private fun observer3() {
        viewModel.localesCercanos().observeForever {
            if (it) {
                hideLoading()
                viewModel.refreshToken()
//                val intent = Intent(requireContext(), AppUser::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
//                startActivity(intent)
                findNavController().navigate(R.id.menu_principal_action)
            } else {
                hideLoading()
            }
        }
    }
}