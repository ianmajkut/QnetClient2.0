package com.qnet.qnetclient.appusuario.ui.fila

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_q_r_fila_usuario.*


class QRFilaUsuarioFragment : Fragment() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout=inflater.inflate(R.layout.fragment_q_r_fila_usuario, container, false)

        val ivBarcode=layout.findViewById<ImageView>(R.id.iv_barcode)

            try {
                val encoder= BarcodeEncoder()
                val bitmap= encoder.encodeBitmap(mAuth.currentUser?.uid, BarcodeFormat.QR_CODE,
                    500, 500)
                ivBarcode.setImageBitmap(bitmap)
            }catch (e:Exception){
                e.printStackTrace()
            }


        return layout

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSalir.setOnClickListener {
            alerta()

        }

    }

    fun alerta(){
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Alerta")
        alertDialog.setMessage("Está a punto de salir de la fila actual. ¿Está seguro?")

        alertDialog.setPositiveButton("Si") { _, _ ->
            findNavController().navigate(R.id.qr_to_fila)
        }
        alertDialog.show()
    }

}