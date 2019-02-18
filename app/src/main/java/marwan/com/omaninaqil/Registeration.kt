package marwan.com.omaninaqil

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import libs.mjn.prettydialog.PrettyDialog

class Registeration : AppCompatActivity() {

    lateinit var name1:EditText
    lateinit var phone:EditText
    lateinit var email:EditText
    lateinit var pass:EditText
    lateinit var repass:EditText
    lateinit var condition:CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        name1 = findViewById(R.id.name_reg)
        phone = findViewById(R.id.phone_reg)
        email = findViewById(R.id.email_reg)
        pass = findViewById(R.id.pass_reg)
        repass = findViewById(R.id.repass_reg)
        condition = findViewById(R.id.codition_reg)
    }
    fun register(view:View){
        checkRegister()
        /*
        var intent = Intent(baseContext, LoginPage::class.java)
        startActivity(intent)
        */
    }
    fun checkRegister(){
        var password:String = pass.text.toString()
        var repassword:String = repass.text.toString()
        if (name1.text.isEmpty()
            || phone.text.isEmpty()
            || email.text.isEmpty()
            || pass.text.isEmpty()
            || repass.text.isEmpty()) {
            showDialog("يجب إدخال جميع البيانات")
        }else if (!condition.isChecked){
            showDialog("يجب الموافقة على الشروط والأحكام لإتمام عملية التسجيل")
        }else if (password != repassword){
            showDialog("كلمة المرور غير متطابقة")
        }else{
            if (!checkPhone(phone.text.toString())){
                showDialog("الرجاء إدخال رقم هاتف صحيح")
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
    fun checkPhone(phoneNum:String) :Boolean {
        var phoneChar:CharArray = phoneNum.toCharArray()
        var charLength:Int = phoneChar.size
        if (charLength != 8){
            return false
        }
        return true
    }
}
