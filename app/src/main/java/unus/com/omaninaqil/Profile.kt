package unus.com.omaninaqil

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
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
import unus.com.Database.DatabaseHandler
import unus.com.model.User
import java.io.ByteArrayInputStream
import java.io.IOException

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
    lateinit var profileImage:ImageView
    private lateinit var progress: CircleArcProgress
    private lateinit var overlay:View
    lateinit var editIcon:ImageButton
    var dbHandler: DatabaseHandler? = null
    private val GALLERY = 1
    private val CAMERA = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        dbHandler = DatabaseHandler(this)
        progress = findViewById(R.id.circleProgress_profile)
        overlay = findViewById(R.id.overlay_view_profile)

        progress.visibility = View.GONE
        overlay.visibility = View.GONE

        nameEdit = findViewById(R.id.name_profile)
        phoneEdit = findViewById(R.id.phone_profile)
        emailEdit = findViewById(R.id.email_profile)
        editSaveBtn = findViewById(R.id.edit_save)
        editIcon = findViewById(R.id.edit_icon)
        profileImage = findViewById(R.id.profile_img)

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
        val img = dbHandler?.getProfileImg()
        val imageStream = ByteArrayInputStream(img)
        val theImage = BitmapFactory.decodeStream(imageStream)
        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, theImage)

        circularBitmapDrawable.isCircular = true
        profileImage.setImageDrawable(circularBitmapDrawable)
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
    fun editImage(view: View){
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }
    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }
    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }
    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)

                    Toast.makeText(this@Profile, "Image Saved!", Toast.LENGTH_SHORT).show()
                    profileImage.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@Profile, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            profileImage.setImageBitmap(thumbnail)

            Toast.makeText(this@Profile, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
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
    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }
}
