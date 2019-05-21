package unus.com.omaninaqil

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.singh.daman.proprogressviews.CircleArcProgress
import lib.kingja.switchbutton.SwitchMultiButton
import java.util.*
import unus.com.Database.DatabaseHandler
import unus.com.Distance.DistanceCalculator
import unus.com.model.Order
import android.widget.AdapterView
import com.facebook.rebound.SpringConfig
import com.jpeng.jpspringmenu.MenuListener
import com.jpeng.jpspringmenu.SpringMenu
import libs.mjn.prettydialog.PrettyDialog
import unus.com.model.LocationTemp


class TransportPackeg : AppCompatActivity() , MenuListener {



    lateinit var weightNumber:TextView
    lateinit var weightLbl:TextView
    lateinit var dateTxt:EditText
    lateinit var timeTxt:EditText
    lateinit var overlay:View
    lateinit var progress:CircleArcProgress
    lateinit var packageType:Spinner
    lateinit var weightSeek:SeekBar
    lateinit var payTypeGroup:RadioGroup

    lateinit var mDateSetLisner:DatePickerDialog.OnDateSetListener
    lateinit var mTimeSetListner:TimePickerDialog.OnTimeSetListener

    lateinit var gov:Spinner
    lateinit var state:Spinner
    lateinit var view12:View
    lateinit var mSpringMenu: SpringMenu

    var placeStatus = "InOut"
    var fromTo = ""

    var dbHandler: DatabaseHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_packeg)

        dbHandler = DatabaseHandler(this)

        weightSeek = findViewById(R.id.weight_seek)

        overlay = findViewById(R.id.overlay_view)
        progress = findViewById(R.id.circleProgress)
        overlay.visibility = View.GONE
        progress.visibility = View.GONE

        weightNumber = findViewById(R.id.weight_txt)
        weightLbl = findViewById(R.id.weight_lbl)
        dateTxt = findViewById(R.id.dateTxt)
        timeTxt = findViewById(R.id.timeTxt)
        packageType = findViewById(R.id.package_type)



        weightSeek.max = 101
       // weightSeek.min = 1
        weightSeek.progress = 1

        weightSeek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    weightNumber.text =  "$p1"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

       /* radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.kg_radio){
                weightSeek.max = 900
                weightSeek.progress = 100
                weightNumber.text = "100"
                weightLbl.text = "كيلو جرام"
            }else if (i == R.id.tn_radio){
                weightSeek.max = 20
                weightSeek.progress = 1
                weightNumber.text = "1"
                weightLbl.text = "طن"
            }
        }*/

        //Date Picker
        dateTxt.setOnClickListener(View.OnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                this@TransportPackeg,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetLisner,
                year, month, day
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        })
        mDateSetLisner = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var month = month
            month += 1


            val date = "$day/$month/$year"
            dateTxt.setText(date)
        }

        //Time Picker
        timeTxt.setOnClickListener(View.OnClickListener {
            val timePickerDialog = TimePickerDialog(this@TransportPackeg,
                TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minutes ->
                    val time = "$hourOfDay:$minutes"
                    timeTxt.setText(time)
                }, 0, 0, false
            )
            timePickerDialog.show()
        })
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
    fun fromBtn(view: View){
        /*
        showTransPlaceDialog()
        fromTo = "from"

        var intent = Intent(baseContext,LocationMap::class.java)
        startActivity(intent)
        */
        dbHandler!!.updatePlaceDir("from")

        showMap()
    }
    fun toBtn(view: View){
        dbHandler!!.updatePlaceDir("to")

        showMap()
    }

    fun showTransPlaceDialog(){
        val dialog = Dialog(this)
        //dialog.setContentView(R.layout.custom_dialog_transtype)
        dialog.setContentView(R.layout.custom_location_layout)
        var switchTabs:SwitchMultiButton = dialog.findViewById(R.id.switchTabs)
        gov = dialog.findViewById(R.id.gov_spin)
        state = dialog.findViewById(R.id.state_spin)
        view12 = dialog.findViewById(R.id.view12)

        /* defaultGov */
        val govAdapter = ArrayAdapter.createFromResource(this, R.array.gov, android.R.layout.simple_spinner_item)
        govAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* muscat */
        val muscatAdapter = ArrayAdapter.createFromResource(this, R.array.musqat, android.R.layout.simple_spinner_item)
        muscatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* musandam */
        val musandamAdapter = ArrayAdapter.createFromResource(this, R.array.musandam, android.R.layout.simple_spinner_item)
        musandamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* burami */
        val buramiAdapter = ArrayAdapter.createFromResource(this, R.array.buraimi, android.R.layout.simple_spinner_item)
        buramiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* dhahira */
        val dhahiraAdapter = ArrayAdapter.createFromResource(this, R.array.dhahirah, android.R.layout.simple_spinner_item)
        dhahiraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* dakhilia */
        val dakhiliaAdapter = ArrayAdapter.createFromResource(this, R.array.dakhiliyah, android.R.layout.simple_spinner_item)
        dakhiliaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* wusta */
        val wustaAdapter = ArrayAdapter.createFromResource(this, R.array.wusta, android.R.layout.simple_spinner_item)
        wustaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* dhofar */
        val dhofarAdapter = ArrayAdapter.createFromResource(this, R.array.dhofar, android.R.layout.simple_spinner_item)
        dhofarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* batinanorth */
        val batinanorthAdapter = ArrayAdapter.createFromResource(this, R.array.batinah_north, android.R.layout.simple_spinner_item)
        batinanorthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* batinasouth */
        val batinasouthAdapter = ArrayAdapter.createFromResource(this, R.array.batinahSouth, android.R.layout.simple_spinner_item)
        batinasouthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* sharnorth */
        val sharnorthAdapter = ArrayAdapter.createFromResource(this, R.array.sharqiyahNorth, android.R.layout.simple_spinner_item)
        sharnorthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* sharsouth */
        val sharsouthAdapter = ArrayAdapter.createFromResource(this, R.array.sharqiyahSouth, android.R.layout.simple_spinner_item)
        sharsouthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* default */
        val defaultAdapter = ArrayAdapter.createFromResource(this, R.array.defult_wilaya, android.R.layout.simple_spinner_item)
        defaultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* outsideCountry */
        val outsideAdapter = ArrayAdapter.createFromResource(this, R.array.outsideOman, android.R.layout.simple_spinner_item)
        outsideAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* uae */
        val uaeAdapter = ArrayAdapter.createFromResource(this, R.array.uae, android.R.layout.simple_spinner_item)
        uaeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        gov.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var selectedItem = parent.getItemAtPosition(position).toString()
                when(selectedItem) {
                    "مسقط" -> state.adapter = muscatAdapter
                    "مسندم" -> state.adapter = musandamAdapter
                    "الظاهرة" -> state.adapter = dhahiraAdapter
                    "الداخلية" -> state.adapter = dakhiliaAdapter
                    "الوسطى" -> state.adapter = wustaAdapter
                    "البريمي" -> state.adapter = buramiAdapter
                    "ظفار" -> state.adapter = dhofarAdapter
                    "الباطنة شمال" -> state.adapter = batinanorthAdapter
                    "الباطنة جنوب" -> state.adapter = batinasouthAdapter
                    "الشرقية شمال" -> state.adapter = sharnorthAdapter
                    "الشرقية جنوب" -> state.adapter = sharsouthAdapter
                    "الإمارات" -> state.adapter = uaeAdapter
                    "اختر المحافظة" -> state.adapter = defaultAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //Another interface callback
            }
        }
        var save :Button = dialog.findViewById(R.id.save_btn_dialog)
        var cancel:Button = dialog.findViewById(R.id.cancel_btn_dialog)

        cancel.setOnClickListener {
            dialog.dismiss()
        }



         switchTabs.setOnSwitchListener { position, tabText ->
             when(tabText){
                 "داخل السلطنة" -> insideOman()
                 "خارج السلطنة" -> setOutsideAdapter()
                 "مناطق الإمتياز" -> specialPlaceDialog()
             }
         }

        var locationObj = LocationTemp()
        save.setOnClickListener {
            if (placeStatus == "In" && fromTo == "from"){
                locationObj.fromLoc = state.selectedItem.toString() + " Oman"
            }else if (placeStatus == "In" && fromTo == "to"){
                locationObj.toLoc = state.selectedItem.toString() + " Oman"
            }else if (placeStatus == "Out" && fromTo == "from"){
                locationObj.fromLoc = state.selectedItem.toString() + " UAE"
            }else if (placeStatus == "Out" && fromTo == "to"){
                locationObj.toLoc = state.selectedItem.toString() + " UAE"
            }
        }

        /*
        var titletxt:TextView = dialog.findViewById(R.id.title_custom_txt)
        var inOman:Button = dialog.findViewById(R.id.inBtn)
        var outOman:Button = dialog.findViewById(R.id.outBtn)
        var specialbtn:Button = dialog.findViewById(R.id.specialBtn)
        inOman.setOnClickListener {
            dialog.dismiss()
            calDistance(23.215717,56.492160,23.590225,58.145446)
            /*var intent = Intent(baseContext,MapsActivity::class.java)
            startActivity(intent)
            */
        }
        outOman.setOnClickListener {
            dialog.dismiss()
            var intent = Intent(baseContext,MapsActivity::class.java)
            startActivity(intent)
        }
        specialbtn.setOnClickListener {
            dialog.dismiss()
            specialplaceDialog()
        }
        */
        dialog.show()
    }
    private fun insideOman(){
        placeStatus = "In"
        state.visibility = View.VISIBLE
        view12.visibility = View.VISIBLE
        /* defaultGov */
        val govAdapter = ArrayAdapter.createFromResource(this, R.array.gov, android.R.layout.simple_spinner_item)
        govAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gov.adapter = govAdapter
    }
    private fun setOutsideAdapter(){
        placeStatus = "Out"
        state.visibility = View.VISIBLE
        view12.visibility = View.VISIBLE
        /* outsideCountry */
        val outsideAdapter = ArrayAdapter.createFromResource(this, R.array.outsideOman, android.R.layout.simple_spinner_item)
        outsideAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /* uae */
        val uaeAdapter = ArrayAdapter.createFromResource(this, R.array.default_outsideRegion, android.R.layout.simple_spinner_item)
        uaeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gov.adapter = outsideAdapter
        state.adapter = uaeAdapter
    }

    private fun specialPlaceDialog() {
        placeStatus = "Special"
        state.visibility = View.GONE
        view12.visibility = View.GONE

        val defaultSpecial = ArrayAdapter.createFromResource(this, R.array.specialPlace, android.R.layout.simple_spinner_item)
        defaultSpecial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gov.adapter = defaultSpecial

    }
    fun save(view: View){



        val order = Order()
        var success:Boolean = false


        var packageId:Int = (Math.random() * 9000000 + 1000000).toInt()
        var date:String = dateTxt.text.toString()
        var time:String = timeTxt.text.toString()
        var type:String = packageType.selectedItem.toString()
        var wight:String = weightSeek.progress.toString()
        var fromLat:String = dbHandler!!.getFromLat(1)
        var fromLon:String = dbHandler!!.getFromLon(1)
        var toLat:String = dbHandler!!.getToLat(1)
        var toLon:String = dbHandler!!.getToLon(1)
        var pay = "cash"
        var price = calculatePrice()
        var status = "processing"

        if (date.isEmpty() || time.isEmpty() || type.isEmpty() || wight.isEmpty()){
            showDialog("الرجاء ادخال جميع البيانات")
        }else{
            order.packegId = packageId.toString()
            order.date = date
            order.time = time
            order.type = type
            order.wight = wight
            order.fromLat = fromLat
            order.fromLon = fromLon
            order.toLat = toLat
            order.toLon = toLon
            order.payType = pay
            order.price = price
            order.status = status



            success = dbHandler!!.addOrder(order)
            if (success){


                var intent = Intent(baseContext,TransRequestFinal::class.java)
                startActivity(intent)
                this.finish()
            }else{
                Toast.makeText(this,"Not Saved", Toast.LENGTH_LONG).show()
            }
        }





    }
    fun calculatePrice():String{
        var fromLat:Double = dbHandler!!.getFromLat(1).toDouble()
        var fromLon:Double = dbHandler!!.getFromLon(1).toDouble()
        var toLat:Double = dbHandler!!.getToLat(1).toDouble()
        var toLon:Double = dbHandler!!.getToLon(1).toDouble()

        var distance = calDistance(fromLat,fromLon,toLat,toLon)
        var price = 0.0
        if (distance < 200){
            price = 0.600 * distance
        }else if (distance < 500 && distance >= 200){
            price = 0.550 * distance
        }else if (distance < 800 && distance >= 500){
            price = 0.500 * distance
        }else{
            price = 0.450 * distance
        }
        return round(price,3).toString()


    }
    fun round(value: Double, places: Int): Double {
        var value = value
        if (places < 0) throw IllegalArgumentException()

        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }
    fun calDistance(lat1:Double,lon1:Double,lat2:Double,lon2:Double):Double{
        var distanceCalculator = DistanceCalculator()
        var disKM = distanceCalculator.greatCircleInKilometers(lat1,lon1,lat2,lon2)
        return disKM
    }
    fun showDialog(msg:String){
        PrettyDialog(this)
            .setTitle("خطأ")
            .setMessage(msg)
            .setIcon(R.drawable.error)
            .show()
    }

    fun showMap(){
        var intent = Intent(baseContext,LocationMap::class.java)
        startActivity(intent)
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
