package com.fes.View.UI.Activity.Rider

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.fes.R
import com.fes.Utils.Loader.LocalModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.*


class PickLocationMaps : AppCompatActivity(), OnMapReadyCallback {
    private val mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var sourceLocationTV: TextView? = null
    private var mCurrLocationMarker: Marker? = null
    private lateinit var mMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_location_maps)
        iniView()
    }

    private fun iniView() {
        sourceLocationTV = findViewById(R.id.sourceLocationTV);
        mapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        if (mapFragment != null) {
            mapFragment!!.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocalModel.instance!!.showProgressDialog(this, "")
        if (mMap != null) {
            if (mFusedLocationProviderClient == null) {
                var mFusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this)

            LocalModel!!.instance!!.showProgressDialog(this, "")
            mFusedLocationProviderClient!!.getLastLocation().addOnSuccessListener(this,
                OnSuccessListener<Any?> { location ->
                    if (location != null) {
                        LocalModel!!.instance!!.cancelProgressDialog()
                        locationFetched(location)
                    }
                }).addOnFailureListener(OnFailureListener {
                LocalModel!!.instance!!.cancelProgressDialog()
            })
        }
        }
        mMap.setOnCameraMoveListener {
            if (mCurrLocationMarker != null) {
                val midLatLng = mMap.cameraPosition.target
                mCurrLocationMarker!!.position=midLatLng
            }else{
                mCurrLocationMarker!!.remove()
                val midLatLng = mMap.cameraPosition.target
                mCurrLocationMarker = mMap.addMarker(
                    MarkerOptions().position(midLatLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                )
            }
        }
        mMap.setOnCameraIdleListener {
            if (mCurrLocationMarker != null) {
                val position: LatLng = mCurrLocationMarker!!.getPosition()
                mCurrLocationMarker!!.position=position
                val Start_Address: String = getCompleteAddressString(
                    applicationContext, position.latitude, position.longitude
                )!!
                sourceLocationTV!!.setText(Start_Address)

            }else{
                val position: LatLng = mCurrLocationMarker!!.getPosition()
                mCurrLocationMarker = mMap.addMarker(
                    MarkerOptions().position(position)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                )
                val Start_Address: String = getCompleteAddressString(
                    applicationContext, position.latitude, position.longitude
                )!!
                sourceLocationTV!!.setText(Start_Address)
            }
        }
    }

    fun mSourceLocationClick(view: View) {}
    fun goBackReq(view: View) {
        val returnIntent = Intent()
        setResult(RESULT_CANCELED, returnIntent)
        finish()
    }

    fun SubmitReq(view: View) {
        val returnIntent = Intent()
        returnIntent.putExtra("LOCATION", sourceLocationTV!!.text)
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    fun getCompleteAddressString(c: Context?, LATITUDE: Double, LONGITUDE: Double): String? {
        var strAdd = "address_Error"
        val geocoder = Geocoder(c, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder()
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
            }
        } catch (e: Exception) {
        }
        return strAdd
    }

    private fun locationFetched(locations: Any) {
        var location = locations as Location
        LocalModel!!.instance!!.cancelProgressDialog()
        if (mMap != null) {
            mMap.clear()
            val currentPosition = LatLng(location.getLatitude(), location.getLongitude())
            val markerOptions = MarkerOptions().position(
                LatLng(
                    location.getLatitude(),
                    location.getLongitude()
                )
            )
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
            markerOptions.position(currentPosition)
            markerOptions.draggable(false)
            mCurrLocationMarker = mMap.addMarker(markerOptions)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
            val Start_Address: String = getCompleteAddressString(
                this,
                currentPosition.latitude,
                currentPosition.longitude
            )!!
            sourceLocationTV!!.setText(Start_Address)

        }
    }
}