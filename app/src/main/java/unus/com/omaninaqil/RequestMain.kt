package unus.com.omaninaqil

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.AdapterView.OnItemClickListener
import com.facebook.rebound.SpringConfig
import com.jpeng.jpspringmenu.MenuListener
import com.jpeng.jpspringmenu.SpringMenu
import libs.mjn.prettydialog.PrettyDialog
import unus.com.Database.DatabaseHandler


class RequestMain : AppCompatActivity(), MenuListener {

    lateinit var shipId:Array<String>
    lateinit var shipStatus:Array<Int>
    lateinit var mSpringMenu: SpringMenu

    var dbHandler: DatabaseHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_main)
        dbHandler = DatabaseHandler(this)
        var requestListView:ListView = findViewById(R.id.list_request_main)

        insertTestData()
        val requestListAdapter = RequestListAdapter(this,shipId,shipStatus)
        requestListView.adapter = requestListAdapter
        requestListView.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                   val item:String = view.findViewById<TextView>(R.id.shipment_id_txt).text.toString()
                   dbHandler!!.updatePackegeId(item)

                    var intent = Intent(baseContext,RequestDetails::class.java)
                    startActivity(intent)

                }
        mSpringMenu = SpringMenu(this, R.layout.view_menu)
        mSpringMenu.setMenuListener(this)
        mSpringMenu.setFadeEnable(true)
        mSpringMenu.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20.0, 5.0))
        mSpringMenu.setDragOffset(0.4f)

        val listBeen = arrayOf<ListBean>(
            ListBean(R.mipmap.home, "الصفحة الرئيسية"),
            ListBean(R.mipmap.trans, "طلب نقل حمولة"),
            ListBean(R.mipmap.track, "تتبع الحمولة"),
            ListBean(R.mipmap.logout, "تسجيل الخروج"),
            ListBean(R.mipmap.setting, "حول التطبيق")
        )
        val adapter = MyAdapter(this, listBeen)
        val listView = mSpringMenu.findViewById(R.id.test_listView) as ListView
        listView.adapter = adapter

    }
    fun menuLeft(view: View){
        mSpringMenu.setDirection(SpringMenu.DIRECTION_LEFT)
        mSpringMenu.openMenu()
    }
    fun scan(view: View){
        PrettyDialog(this)
            .setTitle("قريباً")
            .setMessage("سيتم تفعيل هذه الخدمة قريباً")
            .setIcon(R.drawable.undercons)
            .show()
    }
    fun insertTestData(){

        var shipIdArrayList = dbHandler!!.getOrdersPackegID()

        shipId = shipIdArrayList.toTypedArray()
        var shipStatusArrayList = dbHandler!!.getOrdersStatus()

        shipStatus = shipStatusArrayList.toTypedArray()
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
