package marwan.com.omaninaqil

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import marwan.com.model.User


class MainMenuUser : AppCompatActivity() {

    lateinit var nameProfile:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_user)
        nameProfile = findViewById(R.id.name_txt)

        getProfileDetails()
    }
    fun toTransportPage(view: View){
        var intent = Intent(baseContext, TransportPackeg::class.java)
        startActivity(intent)
    }
    fun trackingType(view: View){
        var intent = Intent(baseContext, RequestMain::class.java)
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
}
