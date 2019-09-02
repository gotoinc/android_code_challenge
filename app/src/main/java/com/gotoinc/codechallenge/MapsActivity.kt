package com.gotoinc.codechallenge

import android.Manifest
import android.Manifest.permission
import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.content.pm.PackageManager.*
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.gotoinc.codechallenge.model.Place
import com.gotoinc.codechallenge.util.LOCATION_REQUEST
import com.gotoinc.codechallenge.util.bitmapDescriptorFromVector
import com.gotoinc.codechallenge.util.toLatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val vm: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            this
        )
    }

    private lateinit var map: GoogleMap

    private val liveLocation = MutableLiveData<LatLng>()
    private val liveSelectedMarker = MutableLiveData<Place>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        liveLocation.observe(this, Observer { setupMap(it) })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.google_maps_style))
        map = googleMap
        updateCurrentLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST && grantResults.all { it == PERMISSION_GRANTED })
            updateCurrentLocation() else setupMap()
    }

    private fun requestLocationPermissions() = requestPermissions(
        this,
        arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), LOCATION_REQUEST
    )

    private fun checkLocationPermissions() =
        checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
                checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED


    private fun updateCurrentLocation() {
        if (checkLocationPermissions()) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                liveLocation.value = it.toLatLng()
            }
                .addOnFailureListener { setupMap() }
        } else requestLocationPermissions()
    }

    private fun setupMap(latLng: LatLng = LatLng(49.988, 36.235)) {
        fun markerFactory(place: Place) =
            MarkerOptions().position(LatLng(place.latitude, place.longitude))
                .icon(this.bitmapDescriptorFromVector(R.drawable.marker_circle, place.markerColor))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        vm.getUpdatedPlaces(latLng).forEach {
            map.addMarker(markerFactory(it)).tag = it.id
        }


        map.setOnMarkerClickListener { marker ->
            liveSelectedMarker.value = vm.places.firstOrNull { it.id == marker.id.toInt() }
            true
        }


    }

}
