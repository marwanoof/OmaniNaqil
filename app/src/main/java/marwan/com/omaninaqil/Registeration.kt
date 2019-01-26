package marwan.com.omaninaqil

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Registeration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
    }
    fun register(view:View){
        var intent = Intent(baseContext, LoginPage::class.java)
        startActivity(intent)
    }
}
