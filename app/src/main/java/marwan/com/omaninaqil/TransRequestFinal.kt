package marwan.com.omaninaqil

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import com.singh.daman.proprogressviews.CircleArcProgress
import marwan.com.Database.DatabaseHandler
import marwan.com.Distance.DistanceCalculator


class TransRequestFinal : AppCompatActivity() {

    lateinit var overlayFinal:View
    lateinit var progressFinal:CircleArcProgress
    lateinit var distanceFinal:TextView
    lateinit var priceFinal:TextView
    var dbHandler: DatabaseHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_request_final)
        dbHandler = DatabaseHandler(this)
        overlayFinal = findViewById(R.id.overlay_view2)
        progressFinal = findViewById(R.id.circleProgress2)
        distanceFinal = findViewById(R.id.distance_final)
        priceFinal = findViewById(R.id.price_final)
        calculatePrice()
    }
    fun calculatePrice(){
       var fromLat:Double = dbHandler!!.getFromLat(1).toDouble()
        var fromLon:Double = dbHandler!!.getFromLon(1).toDouble()
        var toLat:Double = dbHandler!!.getToLat(1).toDouble()
        var toLon:Double = dbHandler!!.getToLon(1).toDouble()

        var distance = calDistance(fromLat,fromLon,toLat,toLon)
        var price = 0.0
        if (distance < 200){
            price = 0.600 * distance
        }else if (distance < 500 && distance >= 200){
            price = 0.550 * distance
        }else if (distance < 800 && distance >= 500){
            price = 0.500 * distance
        }else{
            price = 0.450 * distance
        }
        priceFinal.text = round(price,3).toString()+" ريال عماني"
        distanceFinal.text = round(distance,3).toString()+" km"

    }
    fun calDistance(lat1:Double,lon1:Double,lat2:Double,lon2:Double):Double{
        var distanceCalculator = DistanceCalculator()
        var disKM = distanceCalculator.greatCircleInKilometers(lat1,lon1,lat2,lon2)
        return disKM
    }
    fun confirmFinal(view: View){

        FancyGifDialog.Builder(this)
            .setTitle("هل تريد تأكيد إرسال الطلب؟")
            //.setMessage("This is a granny eating chocolate dialog box. This library is used to help you easily create fancy gify dialog.")
            .setNegativeBtnText("لا")

            .setPositiveBtnBackground("#2c2f45")
            .setPositiveBtnText("نعم")
            .setNegativeBtnBackground("#FFA9A7A8")
            //.setGifResource(R.drawable.movingtruck)   //Pass your Gif here
            .isCancellable(true)
            .OnPositiveClicked {
                showProgress()
            }
            .OnNegativeClicked {

            }
            .build()
    }
    fun submited(){
        FancyGifDialog.Builder(this)
            .setTitle("تم ارسال الطلب بنجاح\nسيتم التواصل معك قريباً")
            .setMessage("بإمكانك متابعة حالة الطلب من خلال صفحة تتبع الطلب")
            .setNegativeBtnText("صفحة تتبع الحمولة")
            .setPositiveBtnBackground("#2c2f45")
            .setPositiveBtnText("حسناً")
            .setNegativeBtnBackground("#FFA9A7A8")
            .setGifResource(R.drawable.movingtruck)   //Pass your Gif here
            .isCancellable(true)
            .OnPositiveClicked {
                var intent = Intent(baseContext,MainMenuUser::class.java)
                startActivity(intent)
            }
            .OnNegativeClicked {
                var intent = Intent(baseContext,RequestMain::class.java)
                startActivity(intent)
            }

            .build()
    }
    fun cancle(view: View){
        FancyGifDialog.Builder(this)
            .setTitle("هل تريد تأكيد إلغاء الطلب؟")
            //.setMessage("This is a granny eating chocolate dialog box. This library is used to help you easily create fancy gify dialog.")
            .setNegativeBtnText("لا")

            .setPositiveBtnBackground("#2c2f45")
            .setPositiveBtnText("نعم")
            .setNegativeBtnBackground("#FFA9A7A8")
            //.setGifResource(R.drawable.movingtruck)   //Pass your Gif here
            .isCancellable(true)
            .OnPositiveClicked {
                var intent = Intent(baseContext,MainMenuUser::class.java)
                startActivity(intent)
                this.finish()
            }
            .OnNegativeClicked {

            }
            .build()
    }
    fun showProgress(){
        overlayFinal.visibility = View.VISIBLE
        progressFinal.visibility = View.VISIBLE
        Handler().postDelayed({
            overlayFinal.visibility = View.GONE
            progressFinal.visibility = View.GONE
            submited()
        }, 3000)
    }

    fun round(value: Double, places: Int): Double {
        var value = value
        if (places < 0) throw IllegalArgumentException()

        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }

}
