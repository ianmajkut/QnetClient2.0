package com.qnet.qnetclient.applocal.ui.info_local

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_register4_local_mapa.*
import kotlinx.android.synthetic.main.fragment_register4_local_mapa.map_view
import kotlin.properties.Delegates

class CambiarUbicacionFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMapLongClickListener {

    private val LOCATION_PERMISSION_ID = 1000
    private lateinit var map: GoogleMap
    private val viewModel = FirestoreViewModel()
    private lateinit var ubicacionLocal: LatLng
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()
    private var marker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_ubicacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchLocal().observe(viewLifecycleOwner, Observer { local ->
            latitude = local.latitud!!.toDouble()
            longitude = local.longitud!!.toDouble()
        })

        bt_confirmar.setOnClickListener {
            viewModel.updateUbicacion(ubicacionLocal.latitude, ubicacionLocal.longitude, false).observeForever {
                findNavController().navigate(R.id.action_cambiarUbicacionFragment_to_infoLocal_Fragment)
            }
        }
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
        map.setOnMarkerClickListener(this)
        map.setOnInfoWindowClickListener(this)
        map.setOnMapLongClickListener(this)
        enableMyLocation()
        map.apply {
            addMarker(
                MarkerOptions()
                    .position(LatLng(latitude, longitude))
                    .title("Mi local")
                    .snippet("Esta es la posicion actual de mi local")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
            moveCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15.0f)
            )
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        map.apply {
            animateCamera(CameraUpdateFactory.newLatLng(marker?.position))
        }
        return false
    }

    override fun onMapLongClick(location: LatLng?) {
        marker?.remove()
        bt_confirmar.visibility = View.VISIBLE
        tv_instrucciones_mapa.visibility = View.GONE
        map.apply {
            marker = addMarker(
                MarkerOptions()
                    .position(location!!)
                    .title("Mi local")
                    .snippet("Persione para confirmar")
            )
        }
        ubicacionLocal = location!!
    }

    override fun onInfoWindowClick(marker: Marker?) {
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