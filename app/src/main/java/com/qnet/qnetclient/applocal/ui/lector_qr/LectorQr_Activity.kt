package com.qnet.qnetclient.applocal.ui.lector_qr

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_usuario.mAuth
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.activity_lector_qr.*

import java.lang.Exception

class LectorQr_Activity : AppCompatActivity() {

    private  val CodigoPermisoCamara=1001
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
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
                sacarUser(code.displayValue, mAuth.currentUser?.uid,true)

            }else{
                textScanResult.text=""
            }
        }
    }

    private fun sacarUser(user:String?, local: String?, llamadaLocal: Boolean) {
        viewModel.sacarUser(user, local, llamadaLocal)
    }
}