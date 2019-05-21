package unus.com.omaninaqil


import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.applemacbookair.locationtracker.LocationTracker

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var tracker:LocationTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        this.setFinishOnTouchOutside(true)
        tracker = LocationTracker(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        var currLocation = LatLng(tracker.latitude,tracker.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 12.0f))
            mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
                override fun onMapClick(latlng: LatLng) {
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(latlng))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12.0f))
                }

            })

        }
        fun saveBtn(view:View){
            finish()
        }
        fun cancelBtn(view: View){
            finish()
        }
    }

