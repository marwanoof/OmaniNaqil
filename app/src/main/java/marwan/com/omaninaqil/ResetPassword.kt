package marwan.com.omaninaqil

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import libs.mjn.prettydialog.PrettyDialog

class ResetPassword : AppCompatActivity() {

    lateinit var email:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        email = findViewById(R.id.email_reset)
    }
    fun send(view:View){
        if (email.text.isEmpty()){
            showDialog("يجب ادخال الإميل")
        }else{
            val auth = FirebaseAuth.getInstance()
            val emailAddress = email.text.toString()

            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        PrettyDialog(this)
                            .setTitle("")
                            .setMessage("تم ارسال اميل لاسترجاع كلمة المرور")
                            .setIcon(R.drawable.successful)
                            .show()
                        Handler().postDelayed({
                            var next = Intent(baseContext, LoginPage::class.java)
                            startActivity(next)
                            this.finish()
                        }, 3000)
                    }
                }
    }
        }
    fun showDialog(msg:String){
        PrettyDialog(this)
            .setTitle("خطأ")
            .setMessage(msg)
            .setIcon(R.drawable.error)
            .show()
    }
}
