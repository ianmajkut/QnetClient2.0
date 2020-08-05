package com.qnet.qnetclient.applocal.ui.info_local

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_local.register3_localDirections
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_cambiar_imagen_local.*
import kotlinx.android.synthetic.main.fragment_cambiar_imagen_local.back_icon
import kotlinx.android.synthetic.main.fragment_cambiar_imagen_local.buttonNext
import kotlinx.android.synthetic.main.fragment_cambiar_imagen_local.image_view
import kotlinx.android.synthetic.main.fragment_register3_local.*

class CambiarImagenLocalFragment : Fragment() {

    private lateinit var viewModel: FirestoreViewModel
    private lateinit var image: Uri
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private val LOCATION_PERMISSION_ID = 1002
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_imagen_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pedirPermiso()
        back_icon.setOnClickListener {

            findNavController().navigate(R.id.action_cambiarImagenLocalFragment_to_infoLocal_Fragment)
        }
        buttonSeleccionar.setOnClickListener {
            elegirImagen()

        }
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_cambiarImagenLocalFragment_to_infoLocal_Fragment)
        }


    }
    private fun pedirPermiso(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE)
            return
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                elegirImagen()
            }
        }
        else{

            Toast.makeText(activity, "PERMISO DENEGADO", Toast.LENGTH_SHORT).show()
        }
    }

    private fun elegirImagen() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if(data?.data!=null) {
                image = data.data!!
                image_view.setImageURI(image)
            }
        }
    }




}