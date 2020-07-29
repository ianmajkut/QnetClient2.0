package com.ian.bottomnavigation.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {

    private val LOCATION_PERMISSION_ID = 1000
    private lateinit var map: GoogleMap
    private val viewModel = FirestoreViewModel()
    private var random = Random()
    private val colores = arrayOf(
        "HUE_AZURE",
        "HUE_BLUE",
        "HUE_CYAN",
        "HUE_GREEN",
        "HUE_MAGENTA",
        "HUE_ORANGE",
        "HUE_RED",
        "HUE_ROSE",
        "HUE_VIOLET",
        "HUE_YELLOW"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            map = it
        }
        map.setMinZoomPreference(10.0f)
        map.setMaxZoomPreference(20.0f)
        map.isBuildingsEnabled = false
        map.isTrafficEnabled = false
        map.isIndoorEnabled = false
        map.uiSettings.isTiltGesturesEnabled = false
        map.uiSettings.isRotateGesturesEnabled = false
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.setOnMarkerClickListener(this)
        enableMyLocation()
        map.apply {
            viewModel.fetchUserData().observe(viewLifecycleOwner, Observer {
                val latUsuario = it.latitud?.toDouble()
                val longUsuario = it.longitud?.toDouble()
                val posicionUsuario = LatLng(latUsuario!!, longUsuario!!)
                moveCamera(
                    CameraUpdateFactory.newLatLngZoom(posicionUsuario, 15.0f)
                )
            })
            viewModel.fetchLocalData().observe(viewLifecycleOwner, Observer { list ->
                list.forEach {
                    val nombreLocal = it.title
                    val descripcionLocal = it.descripcion
                    val latLocal = it.latitud?.toDouble()
                    val longLocal = it.longitud?.toDouble()
                    val posicionLocal = LatLng(latLocal!!, longLocal!!)
                    addMarker(
                        MarkerOptions()
                            .position(posicionLocal)
                            .title(nombreLocal)
                            .snippet(descripcionLocal)
                            .icon(BitmapDescriptorFactory.defaultMarker(random.nextInt(360).toFloat()))
                    )
                }
            })
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(location: Location) {
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        map.apply {
            moveCamera(CameraUpdateFactory.newLatLng(marker?.position))
        }
        return false
    }

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            return
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_ID
            )
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
}