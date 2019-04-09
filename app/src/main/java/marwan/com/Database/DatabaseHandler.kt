package marwan.com.Database
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import marwan.com.model.Order




class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table Orders (id integer primary key , packegId_order text , date_order text , time_order text , type_order text , wight_order text , fromLat text , fromLon text , toLat text, toLon text , pay_order text , status_order text)"
        val tablePlaceDir = "create table PlaceDir (id integer primary key , dir text )"
        val tableLocationCord = "create table LocationCord (id integer primary key , fromLat real, fromLon real, toLat real, toLon real )"
        val lastOrderId = "create table LastId (id integer primary key , order_id integer )"
        db?.execSQL(create_table)
        db?.execSQL(tablePlaceDir)
        db?.execSQL(tableLocationCord)
        db?.execSQL(lastOrderId)
        addRowOnes(db)
        addRowOnesLocation(db)
        addLastIdOnce(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
        // Drop older table if existed
        db?.execSQL("DROP TABLE IF EXISTS Orders")
        db?.execSQL("DROP TABLE IF EXISTS PlaceDir")
        db?.execSQL("DROP TABLE IF EXISTS LocationCord")
        db?.execSQL("DROP TABLE IF EXISTS LastId")
        onCreate(db)
    }

    fun addRowOnes(db:SQLiteDatabase?){

        val valuesPlace = ContentValues()
        valuesPlace.put("dir", "from")
        db?.insert("PlaceDir", null, valuesPlace)
       // db?.close()
    }
    fun addLastIdOnce(db:SQLiteDatabase?){

        val valuesPlace = ContentValues()
        valuesPlace.put("order_id", 1)
        db?.insert("LastId", null, valuesPlace)
        // db?.close()
    }
    fun addRowOnesLocation(db:SQLiteDatabase?){

        val valuesLoc = ContentValues()
        valuesLoc.put("fromLat", "111")
        valuesLoc.put("fromLon", "222")
        valuesLoc.put("toLat", "333")
        valuesLoc.put("toLon", "444")
        db?.insert("LocationCord", null, valuesLoc)
        //db?.close()
    }

    fun updateLocationFrom(lat:Double , lon:Double){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("fromLat", lat)
        values.put("fromLon", lon)

        // updating row
        db.update("LocationCord", values, "id" + " = ?",
            arrayOf("1"))
    }
    fun updateLocationTo(lat:Double , lon:Double){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("toLat", lat)
        values.put("toLon", lon)

        // updating row
        db.update("LocationCord", values, "id" + " = ?",
            arrayOf("1"))
    }
    fun updatePlaceDir(dir:String){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("dir", dir)


        // updating row
        db.update("PlaceDir", values, "id" + " = ?",
            arrayOf("1"))
    }

    //Inserting (Creating) data
    fun addOrder(order: Order): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PACKEGID_ORD, order.packegId)
        values.put(DATE_ORD, order.date)
        values.put(TIME_ORD, order.time)
        values.put(TYPE_ORD, order.type)
        values.put(WIGHT_ORD, order.wight)
        values.put(FROMLat, order.fromLat)
        values.put(FROMLon, order.fromLon)
        values.put(TOLat, order.toLat)
        values.put(ToLon, order.toLon)
        values.put(PAY_ORD, order.payType)
        values.put(STATUS_ORD, order.status)
        val _success = db.insert("Orders", null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getPlaceDir(id: Int): String {
        val db = this.readableDatabase

        val cursor = db.query(
            "PlaceDir", arrayOf("id", "dir"), "id" + "=?",
            arrayOf(id.toString()), null, null, null, null
        )
        cursor?.moveToFirst()

// return contact

        return cursor.getString(1)
    }
    fun getFromLat(id: Int): String {
        val db = this.readableDatabase
        val cursor = db.query(
            "LocationCord", arrayOf("id", "fromLat", "fromLon", "toLat", "toLon"), "id" + "=?",
            arrayOf(id.toString()), null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.getDouble(1).toString()
    }
    fun getFromLon(id: Int): String {
        val db = this.readableDatabase
        val cursor = db.query(
            "LocationCord", arrayOf("id", "fromLat", "fromLon", "toLat", "toLon"), "id" + "=?",
            arrayOf(id.toString()), null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.getDouble(2).toString()
    }
    fun getToLat(id: Int): String {
        val db = this.readableDatabase
        val cursor = db.query(
            "LocationCord", arrayOf("id", "fromLat", "fromLon", "toLat", "toLon"), "id" + "=?",
            arrayOf(id.toString()), null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.getDouble(3).toString()
    }
    fun getToLon(id: Int): String {
        val db = this.readableDatabase
        val cursor = db.query(
            "LocationCord", arrayOf("id", "fromLat", "fromLon", "toLat", "toLon"), "id" + "=?",
            arrayOf(id.toString()), null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.getDouble(4).toString()
    }
    //get all users

    /*fun getAllOrders(): Order {
        var allOrders: String = ""
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_ORD"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID_ORD))
                    var packageID = cursor.getString(cursor.getColumnIndex(PACKEGID_ORD))
                    var date = cursor.getString(cursor.getColumnIndex(DATE_ORD))
                    var time = cursor.getString(cursor.getColumnIndex(TIME_ORD))
                    var type = cursor.getString(cursor.getColumnIndex(TYPE_ORD))
                    var wight = cursor.getString(cursor.getColumnIndex(WIGHT_ORD))
                    var from = cursor.getString(cursor.getColumnIndex(FROM_ORD))
                    var to = cursor.getString(cursor.getColumnIndex(TO_ORD))
                    var pay = cursor.getString(cursor.getColumnIndex(PAY_ORD))
                    var status = cursor.getString(cursor.getColumnIndex(STATUS_ORD))

                    allOrders = "$allOrders\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }*/

    companion object {
        private val DB_NAME = "OmaniNaqilLocal"
        private val DB_VERSIOM = 1
        private val TABLE_ORD = "Order"
        private val ID_ORD = "id_order"
        private val PACKEGID_ORD = "packegId_order"
        private val DATE_ORD = "date_order"
        private val TIME_ORD = "time_order"
        private val TYPE_ORD = "type_order"
        private val WIGHT_ORD = "wight_order"
        private val FROMLat = "fromLat"
        private val FROMLon = "fromLon"
        private val TOLat = "toLat"
        private val ToLon = "toLon"
        private val PAY_ORD = "pay_order"
        private val STATUS_ORD = "status_order"
    }
}