package marwan.com.Database
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import marwan.com.model.Order
import marwan.com.omaninaqil.R


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table Orders (id integer primary key , packegId_order text , date_order text , time_order text , type_order text , wight_order text , fromLat text , fromLon text , toLat text, toLon text , pay_order text , price text , status_order text)"
        val tablePlaceDir = "create table PlaceDir (id integer primary key , dir text )"
        val tableLocationCord = "create table LocationCord (id integer primary key , fromLat real, fromLon real, toLat real, toLon real )"
        val lastOrderId = "create table LastId (id integer primary key , order_id integer )"
        val PackegeId = "create table PackegeId (id integer primary key , pk_id text )"
        val FirstTime = "create table FirstTime (id integer primary key , ft text )"
        db?.execSQL(create_table)
        db?.execSQL(tablePlaceDir)
        db?.execSQL(tableLocationCord)
        db?.execSQL(lastOrderId)
        db?.execSQL(PackegeId)
        db?.execSQL(FirstTime)
        addRowOnes(db)
        addRowOnesLocation(db)
        addLastIdOnce(db)
        addPkIdOnce(db)
        addFirstTime(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
        // Drop older table if existed
        db?.execSQL("DROP TABLE IF EXISTS Orders")
        db?.execSQL("DROP TABLE IF EXISTS PlaceDir")
        db?.execSQL("DROP TABLE IF EXISTS LocationCord")
        db?.execSQL("DROP TABLE IF EXISTS LastId")
        db?.execSQL("DROP TABLE IF EXISTS PackegeId")
        db?.execSQL("DROP TABLE IF EXISTS FirstTime")
        onCreate(db)
    }

    fun addRowOnes(db:SQLiteDatabase?){

        val valuesPlace = ContentValues()
        valuesPlace.put("dir", "from")
        db?.insert("PlaceDir", null, valuesPlace)
       // db?.close()
    }
    fun addFirstTime(db:SQLiteDatabase?){

        val valuesPlace = ContentValues()
        valuesPlace.put("ft", "yes")
        db?.insert("FirstTime", null, valuesPlace)
        // db?.close()
    }
    fun addLastIdOnce(db:SQLiteDatabase?){

        val valuesPlace = ContentValues()
        valuesPlace.put("order_id", 1)
        db?.insert("LastId", null, valuesPlace)
        // db?.close()
    }
    fun addPkIdOnce(db:SQLiteDatabase?){

        val valuesPlace = ContentValues()
        valuesPlace.put("pk_id", "")
        db?.insert("PackegeId", null, valuesPlace)
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
    fun updatePackegeId(pk:String){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("pk_id", pk)


        // updating row
        db.update("PackegeId", values, "id" + " = ?",
            arrayOf("1"))
    }
    fun updateFirstTime(){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("ft", "no")


        // updating row
        db.update("FirstTime", values, "id" + " = ?",
            arrayOf("1"))
    }
    fun updateStatus(pk:String,status:String){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("status_order", status)


        // updating row
        db.update("Orders", values, "packegId_order" + " = ?",
            arrayOf(pk))
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
        values.put("price",order.price)
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
    fun getLastId():Int{
        val db = this.readableDatabase
        val query = "SELECT id from Orders order by id DESC limit 1"
        val c = db.rawQuery(query, null)
        var lastId:Int = 0
        if (c != null && c!!.moveToFirst()) {
            lastId = c!!.getInt(0)
            //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId
    }
    fun getLastPkId():String{
        val db = this.readableDatabase
        val cursor = db.query(
            "PackegeId", arrayOf("id", "pk_id"), "id" + "=?",
            arrayOf("1"), null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.getString(1)
    }
    fun getFirstTime():String{
        val db = this.readableDatabase
        val cursor = db.query(
            "FirstTime", arrayOf("id", "ft"), "id" + "=?",
            arrayOf("1"), null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.getString(1)
    }
    fun getOrder(id: Int): Order {
        val db = this.readableDatabase
        val cursor = db.query(
            "Orders", arrayOf("id", "packegId_order", "date_order" , "time_order"  , "type_order"  , "wight_order"  , "fromLat"  , "fromLon"  , "toLat" , "toLon"  , "pay_order"  , "price" , "status_order"), "id" + "=?",
            arrayOf(id.toString()), null, null, null, null
        )
        cursor?.moveToFirst()
        var order = Order()
        order.packegId = cursor.getString(1)
        order.date = cursor.getString(2)
        order.time = cursor.getString(3)
        order.type = cursor.getString(4)
        order.wight = cursor.getString(5)
        order.fromLat = cursor.getString(6)
        order.fromLon = cursor.getString(7)
        order.toLat = cursor.getString(8)
        order.toLon = cursor.getString(9)
        order.payType = cursor.getString(10)
        order.price = cursor.getString(11)
        order.status = cursor.getString(12)

        return order
    }
    fun getOrderByPk(pk: String): Order {
        val db = this.readableDatabase
        val cursor = db.query(
            "Orders", arrayOf("id", "packegId_order", "date_order" , "time_order"  , "type_order"  , "wight_order"  , "fromLat"  , "fromLon"  , "toLat" , "toLon"  , "pay_order"  , "price" , "status_order"), "packegId_order" + "=?",
            arrayOf(pk), null, null, null, null
        )
        cursor?.moveToFirst()
        var order = Order()
        order.packegId = cursor.getString(1)
        order.date = cursor.getString(2)
        order.time = cursor.getString(3)
        order.type = cursor.getString(4)
        order.wight = cursor.getString(5)
        order.fromLat = cursor.getString(6)
        order.fromLon = cursor.getString(7)
        order.toLat = cursor.getString(8)
        order.toLon = cursor.getString(9)
        order.payType = cursor.getString(10)
        order.price = cursor.getString(11)
        order.status = cursor.getString(12)

        return order
    }
    fun getOrdersPackegID():ArrayList<String>{
        var packeg = ArrayList<String>()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM Orders"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    packeg.add(cursor.getString(cursor.getColumnIndex("packegId_order")))

                } while (cursor.moveToNext())
            }
        }
        return packeg
    }
    fun getOrdersStatus():ArrayList<Int>{
        var packeg = ArrayList<Int>()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM Orders"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    when {
                        cursor.getString(cursor.getColumnIndex("status_order")) == "processing" -> packeg.add(R.drawable.underprocess)
                        cursor.getString(cursor.getColumnIndex("status_order")) == "processed" -> packeg.add(R.drawable.processed)
                        cursor.getString(cursor.getColumnIndex("status_order")) == "deliverd" -> packeg.add(R.drawable.deliverd)
                        cursor.getString(cursor.getColumnIndex("status_order")) == "cancelrequest" -> packeg.add(R.drawable.cancelrequest)

                    }
                } while (cursor.moveToNext())
            }
        }
        return packeg
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
        private val TABLE_ORD = "Orders"
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