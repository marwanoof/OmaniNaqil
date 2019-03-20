package marwan.com.omaninaqil

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.singh.daman.proprogressviews.CircleArcProgress
import java.util.*
import android.widget.DatePicker
import android.widget.TimePicker






class TransportPackeg : AppCompatActivity() {



    lateinit var weightNumber:TextView
    lateinit var weightLbl:TextView
    lateinit var dateTxt:EditText
    lateinit var timeTxt:EditText
    lateinit var overlay:View
    lateinit var progress:CircleArcProgress

    lateinit var mDateSetLisner:DatePickerDialog.OnDateSetListener
    lateinit var mTimeSetListner:TimePickerDialog.OnTimeSetListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_packeg)

        var weightSeek:SeekBar = findViewById(R.id.weight_seek)

        overlay = findViewById(R.id.overlay_view)
        progress = findViewById(R.id.circleProgress)
        overlay.visibility = View.GONE
        progress.visibility = View.GONE

        weightNumber = findViewById(R.id.weight_txt)
        weightLbl = findViewById(R.id.weight_lbl)
        dateTxt = findViewById(R.id.dateTxt)
        timeTxt = findViewById(R.id.timeTxt)



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

    }
    fun fromBtn(view: View){
        showTransPlaceDialog()
    }

    fun showTransPlaceDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_transtype)
        var titletxt:TextView = dialog.findViewById(R.id.title_custom_txt)
        var inOman:Button = dialog.findViewById(R.id.inBtn)
        var outOman:Button = dialog.findViewById(R.id.outBtn)
        var specialbtn:Button = dialog.findViewById(R.id.specialBtn)
        inOman.setOnClickListener {
            dialog.dismiss()
            var intent = Intent(baseContext,MapsActivity::class.java)
            startActivity(intent)
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
        dialog.show()
    }
    fun specialplaceDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_specialplace_dialog)

        var saveBtn: Button = dialog.findViewById(R.id.save_special)
        var cancelBtn: Button = dialog.findViewById(R.id.cancel_special)

        saveBtn.setOnClickListener {
            dialog.dismiss()

        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
            showTransPlaceDialog()



        }
        dialog.show()
    }
    fun save(view: View){
        overlay.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        Handler().postDelayed({
            overlay.visibility = View.GONE
            progress.visibility = View.GONE
            var intent = Intent(baseContext,TransRequestFinal::class.java)
            startActivity(intent)
        }, 3000)
    }
}
