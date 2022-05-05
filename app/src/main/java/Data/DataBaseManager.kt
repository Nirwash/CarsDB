package Data

import Data.DataBaseContract.DataBaseEntry.KEY_NAME
import Data.DataBaseContract.DataBaseEntry.KEY_PRICE
import Data.DataBaseContract.DataBaseEntry.TABLE_NAME
import Model.Car
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Long.getLong

class DataBaseManager(private val context: Context) {
    val dbHelper = DataBaseHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun addCar(car: Car) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, car.name)
            put(KEY_PRICE, car.price)
        }
        db?.insert(TABLE_NAME, null, values)
    }

    fun findCar(searchText: String): Car {
        val db = dbHelper.readableDatabase
        lateinit var car: Car
        val selection =  "$KEY_NAME like ?"
        val selectionArgs = arrayOf("%$searchText%")
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
        cursor.moveToFirst()
        val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
        val price = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE))
        car = Car(name, price)
        Log.d("arrr", "NAME: ${car.name}, PRICE: ${car.price}")
        cursor.close()
        return car
    }

    fun getAllCars(): List<Car> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val cars = mutableListOf<Car>()
        with(cursor) {
            while (moveToNext()) {
                val car = Car(name = getString(1), price = getString(2))
                cars.add(car)
            }
        }
        cursor.close()
        return cars
    }

    fun closeDb() {
        dbHelper.close()
    }

}