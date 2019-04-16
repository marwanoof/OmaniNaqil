package marwan.com.omaninaqil

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.facebook.rebound.SpringConfig
import com.jpeng.jpspringmenu.MenuListener
import com.jpeng.jpspringmenu.SpringMenu


class MainActivity : Activity(), MenuListener, RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    lateinit var mSpringMenu: SpringMenu
    //lateinit var mTitleBar: TitleBar
    lateinit var trans_type: Array<String>
    lateinit var trans_from: Array<String>
    lateinit var trans_to: Array<String>



    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mTitleBar = findViewById(R.id.title_bar)

        var transList : ListView = findViewById(R.id.trans_list)
        //init SpringMenu
        mSpringMenu = SpringMenu(this, R.layout.view_menu)
        mSpringMenu.setMenuListener(this)
        mSpringMenu.setFadeEnable(true)
        mSpringMenu.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20.0, 5.0))
        mSpringMenu.setDragOffset(0.4f)



        val listBeen = arrayOf<ListBean>(
            ListBean(R.mipmap.home, "الصفحة الرئيسية"),
            ListBean(R.mipmap.trans, "طلب نقل حمولة"),
            ListBean(R.mipmap.gold, "تتبع الحمولة"),
            ListBean(R.mipmap.repair, "تسجيل الخروج")
        )
        val adapter = MyAdapter(this, listBeen)
        val listView = mSpringMenu.findViewById(R.id.test_listView) as ListView
        listView.adapter = adapter




        setTestListData()
        val listAdapter = HomeTransListAdapter(this, trans_type, trans_from, trans_to)

        transList.adapter = listAdapter

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

    override fun onCheckedChanged(group: RadioGroup, @IdRes checkedId: Int) {

    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
       }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }

    fun setTestListData(){
        trans_type = arrayOf("نقل حمولة حديد","نقل شحنة طابوق","نقل مياه")
        trans_from = arrayOf("مسقط","عبري","إزكي")
        trans_to = arrayOf("صلالة","مسقط","أدم")
    }
}
