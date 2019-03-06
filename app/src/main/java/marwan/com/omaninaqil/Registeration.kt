package marwan.com.omaninaqil

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import com.singh.daman.proprogressviews.CircleArcProgress
import libs.mjn.prettydialog.PrettyDialog
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v4.content.ContextCompat.startActivity
import android.R.attr.description
import android.R.attr.name
import android.annotation.SuppressLint
import android.util.Log


public class Registeration : AppCompatActivity() {

    lateinit var name1:EditText
    lateinit var phone:EditText
    lateinit var email:EditText
    lateinit var pass:EditText
    lateinit var repass:EditText
    lateinit var condition:CheckBox
    private lateinit var progress: CircleArcProgress
    private lateinit var overlay:View

    var jsonParser = JSONParser()

    private val url_create_product = "https://unus-om.com/naqil/php/add_user.php"

    // JSON Node names
    private val TAG_SUCCESS = "success"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        name1 = findViewById(R.id.name_reg)
        phone = findViewById(R.id.phone_reg)
        email = findViewById(R.id.email_reg)
        pass = findViewById(R.id.pass_reg)
        repass = findViewById(R.id.repass_reg)
        condition = findViewById(R.id.codition_reg)

        progress = findViewById(R.id.circleProgress_reg)
        overlay = findViewById(R.id.overlay_view_reg)

        progress.visibility = View.GONE
        overlay.visibility = View.GONE
    }
    fun register(view:View){
        if (checkRegister()){
            CreateNewUser(this).execute()
        }
        /*
        var intent = Intent(baseContext, LoginPage::class.java)
        startActivity(intent)
        */
    }
    fun checkRegister() :Boolean{
        var password:String = pass.text.toString()
        var repassword:String = repass.text.toString()
        if (name1.text.isEmpty()
            || phone.text.isEmpty()
            || email.text.isEmpty()
            || pass.text.isEmpty()
            || repass.text.isEmpty()) {
            showDialog("يجب إدخال جميع البيانات")
            return false
        }else if (!condition.isChecked){
            showDialog("يجب الموافقة على الشروط والأحكام لإتمام عملية التسجيل")
            return false
        }else if (password != repassword){
            showDialog("كلمة المرور غير متطابقة")
            return false
        }else{
            if (!checkPhone(phone.text.toString())){
                showDialog("الرجاء إدخال رقم هاتف صحيح")
                return false
            }
        }
        return true
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

    @SuppressLint("StaticFieldLeak")
    class CreateNewUser(private var activity:Registeration?) : AsyncTask<String, String, String>() {


        override fun onPreExecute() {
            super.onPreExecute()
            activity?.progress?.visibility = View.VISIBLE
            activity?.overlay?.visibility = View.VISIBLE
        }
        override fun doInBackground(vararg args: String?): String {
            // Building Parameters
            var name:String = activity?.name1?.text.toString()
            var email:String = activity?.email?.text.toString()
            var pass:String = activity?.pass?.text.toString()
            var phone:String = activity?.phone?.text.toString()
            var status = "approved"

            val params = ArrayList<NameValuePair>()
            params.add(BasicNameValuePair("name", name))
            params.add(BasicNameValuePair("email", email))
            params.add(BasicNameValuePair("phone", phone))
            params.add(BasicNameValuePair("pass", pass))
            params.add(BasicNameValuePair("status", status))

            // getting JSON Object
            // Note that create product url accepts POST method
            val json = activity?.jsonParser?.makeHttpRequest(
                activity?.url_create_product,
                "POST", params
            )

            // check log cat fro response
            Log.d("Create Response", json.toString())

            // check for success tag
            try {
                val success = json?.getInt(activity?.TAG_SUCCESS)

                if (success == 1) {
                    // successfully created product
                    var i = Intent(activity?.baseContext, LoginPage::class.java)
                    activity?.startActivity(i)

                    // closing this screen
                    activity?.finish()
                } else {
                    // failed to create product
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }


            return null.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            activity?.progress?.visibility = View.GONE
            activity?.overlay?.visibility = View.GONE
        }



    }
}
