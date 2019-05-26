package unus.com.omaninaqil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ListView
import com.facebook.rebound.SpringConfig
import com.jpeng.jpspringmenu.MenuListener
import com.jpeng.jpspringmenu.SpringMenu

class AboutApp : AppCompatActivity() , MenuListener {
    lateinit var mSpringMenu: SpringMenu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
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
