package marwan.com.omaninaqil

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import com.singh.daman.proprogressviews.CircleArcProgress


class TransRequestFinal : AppCompatActivity() {

    lateinit var overlayFinal:View
    lateinit var progressFinal:CircleArcProgress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_request_final)
        overlayFinal = findViewById(R.id.overlay_view2)
        progressFinal = findViewById(R.id.circleProgress2)
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
    fun showProgress(){
        overlayFinal.visibility = View.VISIBLE
        progressFinal.visibility = View.VISIBLE
        Handler().postDelayed({
            overlayFinal.visibility = View.GONE
            progressFinal.visibility = View.GONE
            submited()
        }, 3000)
    }
}
