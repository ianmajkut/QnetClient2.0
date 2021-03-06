package com.qnet.qnetclient.applocal.ui.lector_qr

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.navigation.fragment.findNavController
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.auth.FirebaseAuth
import com.qnet.qnetclient.R
import com.qnet.qnetclient.applocal.AppLocal
import com.qnet.qnetclient.data.classes.Usuario
import com.qnet.qnetclient.local_o_usuario
import com.qnet.qnetclient.loginregister_usuario.CommonUtils
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.activity_lector_qr.*

import java.lang.Exception

class LectorQr_Activity : AppCompatActivity() {

    private  val CodigoPermisoCamara=1001
    private var loadingDialog: Dialog? = null
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private lateinit var mAuth: FirebaseAuth
    private val viewModel by lazy { FirestoreViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lector_qr)
        if(ContextCompat.checkSelfPermission(
                this@LectorQr_Activity,Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){

            pedirPermisoCamara()
        }else{
            setupControls()
        }

    }


    private fun setupControls(){

        detector= BarcodeDetector.Builder(this@LectorQr_Activity).build()
        cameraSource= CameraSource.Builder(this@LectorQr_Activity, detector)
            .setAutoFocusEnabled(true)
            .build()
        cameraSurfaceView.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)

    }


    private fun pedirPermisoCamara(){
        ActivityCompat.requestPermissions(this@LectorQr_Activity, arrayOf(Manifest.permission.CAMERA),
            CodigoPermisoCamara)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==CodigoPermisoCamara && grantResults.isNotEmpty()){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                setupControls()
            }else{
                Toast.makeText(applicationContext, "Permiso Denegado", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private val surfaceCallBack= object : SurfaceHolder.Callback{
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            cameraSource.stop()
        }


        override fun surfaceCreated(surfaceHolder:  SurfaceHolder?) {
            try {
                if (ActivityCompat.checkSelfPermission(
                        this@LectorQr_Activity,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this@LectorQr_Activity,
                        arrayOf(android.Manifest.permission.CAMERA),
                        CodigoPermisoCamara)
                    return
                }
                cameraSource.start(surfaceHolder)
            }catch (exception:Exception){
                Toast.makeText(applicationContext, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private  val processor = object : Detector.Processor<Barcode>{
        override fun release() {
        }

        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {

            if(detections!=null && detections.detectedItems.isNotEmpty()){
                val qrCode: SparseArray<Barcode> = detections.detectedItems
                val code= qrCode.valueAt(0)
                textScanResult.text=code.displayValue
                mAuth = FirebaseAuth.getInstance()
                sacarUser(code.displayValue, mAuth.currentUser?.uid,true)
            }else{
                textScanResult.text=""
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun sacarUser(user:String?, local: String?, llamadaLocal: Boolean) {
        Handler(Looper.getMainLooper()).post{
            cameraSource.stop()
            showLoading()
            viewModel.sacarUser(user).observeForever {
                if (it!=null) {
                    if (it.position != null) {
                        hideLoading()
                        alerta(user, local, llamadaLocal, it)
                    }else{
                        Toast.makeText(this, "Usuario no esta en la cola", Toast.LENGTH_LONG).show()
                        hideLoading()
                        val intent = Intent(this, AppLocal::class.java)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    Toast.makeText(this, "Error al leer Qr", Toast.LENGTH_SHORT).show()
                    hideLoading()
                    val intent = Intent(this, AppLocal::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    fun alerta(user:String?, local: String?, llamadaLocal: Boolean, usuario: Usuario?){
        val alertDialog = AlertDialog.Builder(this)
        if (usuario?.name != null && usuario.position != null) {
            alertDialog.setTitle("Sacar de la Cola")
            alertDialog.setMessage(
                "Está a punto de sacar a ${usuario.name} que esta en la posicion ${(usuario.position!! +1)}" +
                        "¿Esta seguro?"
            )

            alertDialog.setNegativeButton("No") { _, _ ->
                Toast.makeText(this, "No", Toast.LENGTH_LONG).show()
                hideLoading()
                val intent = Intent(this, AppLocal::class.java)
                startActivity(intent)
                finish()
            }
            alertDialog.setPositiveButton("Si") { _, _ ->
                viewModel.sacarUser(user, local, llamadaLocal).observeForever {
                    Toast.makeText(this, "Usuario Removido", Toast.LENGTH_LONG).show()
                    hideLoading()
                    val intent = Intent(this, AppLocal::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            alertDialog.show()
        }else{
            alertDialog.setTitle("Error")
            alertDialog.setMessage(
                "Usuario no existe en la cola"
            )
            alertDialog.setPositiveButton("Ok") { _, _ ->
                Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show()
                hideLoading()
                val intent = Intent(this, AppLocal::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(this)
    }

}