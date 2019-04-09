package marwan.com.omaninaqil

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.Toast
import android.widget.TextView
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import marwan.com.Database.DatabaseHandler


class RequestMain : AppCompatActivity() {

    lateinit var shipId:Array<String>
    lateinit var shipStatus:Array<Int>
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

    }

    fun insertTestData(){

        var shipIdArrayList = dbHandler!!.getOrdersPackegID()

        shipId = shipIdArrayList.toTypedArray()
        var shipStatusArrayList = dbHandler!!.getOrdersStatus()

        shipStatus = shipStatusArrayList.toTypedArray()
    }
}
