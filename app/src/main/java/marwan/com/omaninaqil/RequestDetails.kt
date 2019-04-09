package marwan.com.omaninaqil

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import libs.mjn.prettydialog.PrettyDialog
import marwan.com.Database.DatabaseHandler
import marwan.com.model.Order


class RequestDetails : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var gmap: GoogleMap
    lateinit var price:TextView
    lateinit var type:TextView
    lateinit var weight:TextView
    lateinit var status:TextView
    lateinit var statusTem:String
    lateinit var confirmBtn:Button
    lateinit var lastPackeg:String

    private val MAP_VIEW_BUNDLE_KEY = "AIzaSyC9YdUImHHK-A75LRtao72ob7anpFmbwtg"
    var dbHandler: DatabaseHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)
        dbHandler = DatabaseHandler(this)
        price = findViewById(R.id.price_final)
        type = findViewById(R.id.type_final)
        weight = findViewById(R.id.weight_final)
        status = findViewById(R.id.status_final)
        confirmBtn = findViewById(R.id.confirm_detaile_btn)
        lastPackeg = dbHandler!!.getLastPkId()
        getDetails(lastPackeg)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView = findViewById(R.id.details_map)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
    }
    fun getDetails(pk:String){
        var order:Order = dbHandler!!.getOrderByPk(pk)
        price.text = order.price+" ريال عماني"
        type.text = order.type
        weight.text = order.wight+" طن"
        statusTem = order.status
        when (statusTem) {
            "processing" -> status.text = "الطلب قيد المعالجة"
            "processed" -> status.text = "الحمولة قيد التوصيل"
            "deliverd" -> status.text = "تم توصيل الحمولة"
            "cancelrequest" -> status.text = "تم إلغاء الطلب"
        }

    }
    fun confirm(view:View){
        if (statusTem == "processing"){
            showDialog("الطلب قيد المعالجة لايمكن تأكيد وصول الحمولة")
        }else if (statusTem == "cancelrequest"){
            showDialog("تم إلغاء الطلب لايمكن تأكيد وصول الحمولة")
        }else{
            FancyGifDialog.Builder(this)
                .setTitle("هل تريد تأكيد وصول الحمولة؟")
                //.setMessage("This is a granny eating chocolate dialog box. This library is used to help you easily create fancy gify dialog.")
                .setNegativeBtnText("لا")

                .setPositiveBtnBackground("#2c2f45")
                .setPositiveBtnText("نعم")
                .setNegativeBtnBackground("#FFA9A7A8")
                //.setGifResource(R.drawable.movingtruck)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked {
                    updateStatus()
                    confirmBtn.visibility = View.GONE
                }
                .OnNegativeClicked {

                }
                .build()
        }
    }
    fun updateStatus(){
        dbHandler!!.updateStatus(lastPackeg,"deliverd")
    }
    fun showDialog(msg:String){
        PrettyDialog(this)
            .setTitle("خطأ")
            .setMessage(msg)
            .setIcon(R.drawable.error)
            .show()
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
