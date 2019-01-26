package marwan.com.omaninaqil

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
    }
    fun newAccountAction(view: View){
        var intent = Intent(baseContext, Registeration::class.java)
        startActivity(intent)
    }
    fun loginCheck(view: View){
        var intent = Intent(baseContext, MainMenuUser::class.java)
        startActivity(intent)
    }
}
