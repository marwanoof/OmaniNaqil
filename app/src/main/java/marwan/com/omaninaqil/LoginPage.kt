package marwan.com.omaninaqil

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.singh.daman.proprogressviews.CircleArcProgress
import libs.mjn.prettydialog.PrettyDialog


class LoginPage : AppCompatActivity() {

    lateinit var emailLogin:EditText
    lateinit var  passLogin:EditText
    private lateinit var progress: CircleArcProgress
    private lateinit var overlay:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        emailLogin = findViewById(R.id.emailEdit)
        passLogin = findViewById(R.id.passEdit)

        progress = findViewById(R.id.circleProgress_login)
        overlay = findViewById(R.id.overlay_view_login)

        progress.visibility = View.GONE
        overlay.visibility = View.GONE
    }
    fun newAccountAction(view: View){
        var intent = Intent(baseContext, Registeration::class.java)
        startActivity(intent)
    }
    fun loginCheck(view: View){

        if (emailLogin.text.isEmpty() || passLogin.text.isEmpty()){
            showDialog("يجب إدخال جميع البيانات")
        }else{
            progress.visibility = View.VISIBLE
            overlay.visibility = View.VISIBLE
            val mAuth = FirebaseAuth.getInstance()
            var emailFirebase:String = emailLogin.text.toString()
            var passFirebase:String = passLogin.text.toString()
            mAuth.signInWithEmailAndPassword(emailFirebase, passFirebase)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        progress.visibility = View.GONE
                        overlay.visibility = View.GONE
                        var next = Intent(baseContext,MainMenuUser::class.java)
                        startActivity(next)
                        this.finish()
                    } else {
                        showDialog("حدث خطأ أثناء تسجيل الدخول الرجاء المحاولة مرة أخرى")
                        progress.visibility = View.GONE
                        overlay.visibility = View.GONE
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
