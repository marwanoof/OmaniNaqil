package marwan.com.omaninaqil

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View



class MainMenuUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_user)
    }
    fun toTransportPage(view: View){
        var intent = Intent(baseContext, TransportPackeg::class.java)
        startActivity(intent)
    }
    fun trackingType(view: View){
        var intent = Intent(baseContext, RequestMain::class.java)
        startActivity(intent)
        /*
        FancyAlertDialog.Builder(this)
            .setTitle("اختر نوع التتبع")
            .setBackgroundColor(Color.parseColor("#2c2f45"))  //Don't pass R.color.colorvalue
            //.setMessage("Do you really want to Exit ?")
            .setNegativeBtnText("تتبع حمولة سابقة")
            .setPositiveBtnBackground(Color.parseColor("#f36823"))  //Don't pass R.color.colorvalue
            .setPositiveBtnText("تتبع حمولة حديثة")
            .setNegativeBtnBackground(Color.parseColor("#ff9000"))  //Don't pass R.color.colorvalue
            .setAnimation(Animation.POP)
            .isCancellable(true)
            .setIcon(R.drawable.trackingicon, Icon.Visible)
            .OnPositiveClicked {

                 }
            .OnNegativeClicked {

            }
            .build()
            */
    }
}
