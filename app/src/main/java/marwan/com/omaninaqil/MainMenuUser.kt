package marwan.com.omaninaqil

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import com.facebook.rebound.SpringConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jpeng.jpspringmenu.MenuListener
import com.jpeng.jpspringmenu.SpringMenu
import marwan.com.model.User


@SuppressLint("ParcelCreator")
class MainMenuUser : AppCompatActivity() , MenuListener, Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    lateinit var nameProfile:TextView
    lateinit var mSpringMenu: SpringMenu



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_user)
        nameProfile = findViewById(R.id.name_txt)
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
        getProfileDetails()
    }
    fun menuLeft(view: View){
        mSpringMenu.setDirection(SpringMenu.DIRECTION_LEFT)
        mSpringMenu.openMenu()
    }
    fun toTransportPage(view: View){
        var intent = Intent(baseContext, TransportPackeg::class.java)
        startActivity(intent)
    }
    fun trackingType(view: View){
        var intent = Intent(baseContext, RequestMain::class.java)
        startActivity(intent)

    }
    fun toProfile(view: View){
        var intent = Intent(baseContext, Profile::class.java)
        startActivity(intent)

    }
    fun getProfileDetails(){
        val mDatabase = FirebaseDatabase.getInstance().getReference("users")
        val mAuth = FirebaseAuth.getInstance()
        var userui: FirebaseUser = mAuth.currentUser!!
        mDatabase.child(userui.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)

                nameProfile.text = user!!.name
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
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
