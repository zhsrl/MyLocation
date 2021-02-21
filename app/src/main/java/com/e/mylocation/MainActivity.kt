package com.e.mylocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.material.button.MaterialButton
import java.util.concurrent.Executor
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {

    private lateinit var viewLocation: TextView
    private lateinit var getLocation: MaterialButton

    private lateinit var locationManager: LocationManager

    private var FINE_LOCATION: Array<String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    var LOCATION_REFRESH_TIME: Long = 0
    var LOCATION_REFRESH_DISTANCE: Float = 0.1F



    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewLocation = findViewById(R.id.TV_location_view)
        getLocation = findViewById(R.id.BTN_get_location)

        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        getLocation.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(FINE_LOCATION, 255)

                return@setOnClickListener
            }

            Toast.makeText(applicationContext, "Location tapped", Toast.LENGTH_SHORT).show()

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE,
                    locationListener())
        }



    }

    private fun locationListener(): LocationListener = object : LocationListener{
        override fun onLocationChanged(p0: Location) {
            viewLocation.text = "Your location: " + p0.latitude.toString() + ", " + p0.longitude.toString()
            Log.e("LOCATION : ", "Your location: " + p0.latitude.toString() + ", " + p0.longitude.toString())
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

    }
}