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



class RequestMain : AppCompatActivity() {

    lateinit var shipId:Array<String>
    lateinit var shipStatus:Array<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_main)
        var requestListView:ListView = findViewById(R.id.list_request_main)

        insertTestData()
        val requestListAdapter = RequestListAdapter(this,shipId,shipStatus)
        requestListView.adapter = requestListAdapter
        requestListView.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                   // val item = (view as TextView).text.toString()
                    var intent = Intent(baseContext,RequestDetails::class.java)
                    startActivity(intent)

                }

    }

    fun insertTestData(){
        shipId = arrayOf("06325412","052148532","02875479","04857269")
        shipStatus = arrayOf(R.drawable.underprocess,R.drawable.processed,R.drawable.deliverd,R.drawable.cancelrequest)
    }
}
