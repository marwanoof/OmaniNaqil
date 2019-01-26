package marwan.com.omaninaqil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.BitmapDescriptorFactory






class RequestDetails : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var gmap: GoogleMap

    private val MAP_VIEW_BUNDLE_KEY = "AIzaSyC9YdUImHHK-A75LRtao72ob7anpFmbwtg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView = findViewById(R.id.details_map)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap.setMinZoomPreference(12.0f)
        val ny = LatLng(23.540832, 58.188538)
        gmap.addMarker(MarkerOptions().position(ny).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarkertruck)))

        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny))



        gmap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }
}
