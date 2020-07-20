package com.qnet.qnetclient.appusuario.ui.fila

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.qnet.qnetclient.R


class QRFilaUsuarioFragment : Fragment() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout=inflater.inflate(R.layout.fragment_q_r_fila_usuario, container, false)

        val etText = layout.findViewById<EditText>(R.id.et_text)
        val btnGen=layout.findViewById<Button>(R.id.btn_generate)
        val ivBarcode=layout.findViewById<ImageView>(R.id.iv_barcode)

        btnGen.setOnClickListener {
            try {
                val encoder= BarcodeEncoder()
                val bitmap= encoder.encodeBitmap(mAuth.currentUser?.uid, BarcodeFormat.QR_CODE,
                    500, 500)
                ivBarcode.setImageBitmap(bitmap)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }


        return layout

    }

}