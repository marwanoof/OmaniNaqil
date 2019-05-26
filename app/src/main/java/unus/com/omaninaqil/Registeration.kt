package unus.com.omaninaqil


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.view.View
import android.widget.CheckBox
import android.widget.EditText

import com.singh.daman.proprogressviews.CircleArcProgress
import libs.mjn.prettydialog.PrettyDialog


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import unus.com.model.User
import android.graphics.Bitmap

import android.graphics.BitmapFactory
import android.text.Html
import android.view.MotionEvent
import android.widget.ListView
import com.facebook.rebound.SpringConfig
import com.jpeng.jpspringmenu.MenuListener
import com.jpeng.jpspringmenu.SpringMenu
import unus.com.Database.DatabaseHandler
import java.io.ByteArrayOutputStream


class Registeration : AppCompatActivity() , MenuListener {


    lateinit var name1:EditText
    lateinit var phone:EditText
    lateinit var email:EditText
    lateinit var pass:EditText
    lateinit var repass:EditText
    lateinit var condition:CheckBox
    private lateinit var progress: CircleArcProgress
    private lateinit var overlay:View
    lateinit var mSpringMenu: SpringMenu
    var dbHandler: DatabaseHandler? = null

    //private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        mSpringMenu = SpringMenu(this, R.layout.view_menu)
        mSpringMenu.setMenuListener(this)
        mSpringMenu.setFadeEnable(true)
        mSpringMenu.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20.0, 5.0))
        mSpringMenu.setDragOffset(0.4f)

        val listBeen = arrayOf<ListBean>(
            ListBean(R.mipmap.loginicon, "تسجيل الدخول"),
            ListBean(R.mipmap.newsier, "مستخدم جديد")

        )
        val adapter = MyAdapter(this, listBeen)
        val listView = mSpringMenu.findViewById(R.id.test_listView) as ListView
        listView.adapter = adapter

        dbHandler = DatabaseHandler(this)
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

       //mAuth = FirebaseAuth.getInstance()
       // database = FirebaseDatabase.getInstance().reference
    }

    fun menuLeft(view: View){
        mSpringMenu.setDirection(SpringMenu.DIRECTION_LEFT)
        mSpringMenu.openMenu()
    }
    override fun onStart() {
        super.onStart()
        //val currentUser = mAuth.currentUser
        //updateUI(currentUser)
    }
    fun register(view:View){
        if (checkRegister()){
            signUp()
            //CreateNewUser(this).execute()
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
    fun signUp(){
        saveDefultProfileImg()
        progress.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE
        var emailFirebase:String = email.text.toString()
        var passFirebase:String = pass.text.toString()
        var nameFirebase:String = name1.text.toString()
        var phoneFirebase:String =phone.text.toString()
        val mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(emailFirebase, passFirebase)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    var userui: FirebaseUser = mAuth.currentUser!!

                    saveToDatabase(userui.uid,nameFirebase,emailFirebase,phoneFirebase)
                    progress.visibility = View.GONE
                    overlay.visibility = View.GONE

                    PrettyDialog(this)
                        .setTitle("")
                        .setMessage("تم التسجيل بنجاح")
                        .setIcon(R.drawable.successful)
                        .show()

                    Handler().postDelayed({
                        var next = Intent(baseContext,LoginPage::class.java)
                        startActivity(next)
                        this.finish()
                    }, 2000)
                } else {

                    showDialog("حدث خطأ أثناء التسجيل الرجاء المحاولة مرة أخرى")
                    progress.visibility = View.GONE
                    overlay.visibility = View.GONE
                }


            }
    }
    fun saveDefultProfileImg(){
        val image = BitmapFactory.decodeResource(
            resources,
            R.drawable.userprofile
        )

        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val imageInByte = stream.toByteArray()
        dbHandler?.addProfileImg(imageInByte)
    }
    fun saveToDatabase(userid:String, name:String, email:String, phone:String){

        val mDatabase = FirebaseDatabase.getInstance().getReference("users")


        val userId = mDatabase.push().key


        val user = User(name, email, phone)

// pushing user to 'users' node using the userId
        mDatabase.child(userid).setValue(user)
    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return mSpringMenu.dispatchTouchEvent(ev)
    }

    override fun onMenuOpen() {
    }

    override fun onMenuClose() {
    }

    override fun onProgressUpdate(value: Float, bouncing: Boolean) {

    }

    fun privacyView(view: View){
        PrettyDialog(this)
            .setTitle("الشروط والأحكام")
            .setMessage(Html.fromHtml(getString(R.string.privacy)).toString())
            .setIcon(R.drawable.privacy)
            .show()
    }
/*
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
    */
}
