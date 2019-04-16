package marwan.com.omaninaqil

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.facebook.rebound.SpringConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jpeng.jpspringmenu.MenuListener
import com.jpeng.jpspringmenu.SpringMenu
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import com.singh.daman.proprogressviews.CircleArcProgress
import marwan.com.model.User

@SuppressLint("ParcelCreator")
class Profile : AppCompatActivity() , MenuListener, Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    lateinit var mSpringMenu: SpringMenu
    lateinit var nameEdit:EditText
    lateinit var phoneEdit:EditText
    lateinit var emailEdit:EditText
    lateinit var editSaveBtn:Button
    private lateinit var progress: CircleArcProgress
    private lateinit var overlay:View
    lateinit var editIcon:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        progress = findViewById(R.id.circleProgress_profile)
        overlay = findViewById(R.id.overlay_view_profile)

        progress.visibility = View.GONE
        overlay.visibility = View.GONE

        nameEdit = findViewById(R.id.name_profile)
        phoneEdit = findViewById(R.id.phone_profile)
        emailEdit = findViewById(R.id.email_profile)
        editSaveBtn = findViewById(R.id.edit_save)
        editIcon = findViewById(R.id.edit_icon)

        mSpringMenu = SpringMenu(this, R.layout.view_menu)
        mSpringMenu.setMenuListener(this)
        mSpringMenu.setFadeEnable(true)
        mSpringMenu.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20.0, 5.0))
        mSpringMenu.setDragOffset(0.4f)
        val listBeen = arrayOf<ListBean>(
            ListBean(R.mipmap.home, "الصفحة الرئيسية"),
            ListBean(R.mipmap.trans, "طلب نقل حمولة"),
            ListBean(R.mipmap.track, "تتبع الحمولة"),
            ListBean(R.mipmap.logout, "تسجيل الخروج")
        )
        val adapter = MyAdapter(this, listBeen)
        val listView = mSpringMenu.findViewById(R.id.test_listView) as ListView
        listView.adapter = adapter
        getData()
    }
    fun getData(){
        progress.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE
        val mDatabase = FirebaseDatabase.getInstance().getReference("users")
        val mAuth = FirebaseAuth.getInstance()
        var userui: FirebaseUser = mAuth.currentUser!!
        mDatabase.child(userui.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)

                progress.visibility = View.GONE
                overlay.visibility = View.GONE
                nameEdit.setText(user!!.name,TextView.BufferType.EDITABLE)
                phoneEdit.setText(user!!.phone,TextView.BufferType.EDITABLE)
                emailEdit.setText(user!!.email,TextView.BufferType.EDITABLE)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
    }

    fun editBtn(view: View){
        var btnText:String = editSaveBtn.text.toString()

        if (btnText == "تعديل"){
            editIcon.visibility = View.VISIBLE
            nameEdit.isEnabled = true
            phoneEdit.isEnabled = true
            editSaveBtn.text = "حفظ"
        }else if (btnText == "حفظ"){
            FancyGifDialog.Builder(this)
                .setTitle("هل تريد حفظ التغييرات؟")
                //.setMessage("This is a granny eating chocolate dialog box. This library is used to help you easily create fancy gify dialog.")
                .setNegativeBtnText("لا")

                .setPositiveBtnBackground("#2c2f45")
                .setPositiveBtnText("نعم")
                .setNegativeBtnBackground("#FFA9A7A8")
                //.setGifResource(R.drawable.movingtruck)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked {
                    editSaveBtn.text = "تعديل"
                    editIcon.visibility = View.GONE
                    nameEdit.isEnabled = false
                    phoneEdit.isEnabled = false
                    update()
                }
                .OnNegativeClicked {

                }
                .build()
        }
    }
    fun update(){
        progress.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE
    }
    fun menuLeft(view: View){
        mSpringMenu.setDirection(SpringMenu.DIRECTION_LEFT)
        mSpringMenu.openMenu()
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
}
