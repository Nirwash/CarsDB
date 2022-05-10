package data

import data.DatabaseContract.DatabaseEntry.KEY_ID
import data.DatabaseContract.DatabaseEntry.KEY_NAME
import data.DatabaseContract.DatabaseEntry.KEY_PRICE
import data.DatabaseContract.DatabaseEntry.TABLE_NAME
import model.Car
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class DatabaseManager(context: Context) {
    private val dbHelper = DatabaseHandler(context)


    fun addCar(car: Car) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, car.name)
            put(KEY_PRICE, car.price)
        }
        db?.insert(TABLE_NAME, null, values)
        Log.d("MyCar", "add NAME: ${car.name}, PRICE: ${car.price}")
        db.close()
    }

    fun getCar(searchText: String): Car {
        val db = dbHelper.readableDatabase
        lateinit var car: Car
        val selection =  "$KEY_NAME like ?"
        try {
            val selectionArgs = arrayOf("%$searchText%")
            val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
            cursor.moveToFirst()
            val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE))
            car = Car(name = name, price = price)
            Log.d("MyCar", "getFromName - NAME: ${car.name}, PRICE: ${car.price}")
            cursor.close()
            return car
        } catch (e: Exception) {
            Log.d("MyCar", "car has been deleted")
        }
        return Car(0, "", "")

    }

    fun getCar(id: Int): Car {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(KEY_ID, KEY_NAME, KEY_PRICE)
        val selection = "$KEY_ID=?"
        try {
            val selectionArgs = arrayOf(id.toString())
            val cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, null)
            cursor?.moveToFirst()
            val carId = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)).toInt()
            val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE))
            val car = Car(carId, name, price)
            Log.d("MyCar", "getFromId - ID: ${car.id}, NAME: ${car.name}, PRICE: ${car.price}")
            cursor.close()
            return car
        } catch (e: Exception) {
            Log.d("MyCar", "car has been deleted")
        }
        return Car(0, "", "")
    }

    fun getAllCars(): List<Car> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val cars = mutableListOf<Car>()
        with(cursor) {
            while (moveToNext()) {
                val carId = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)).toInt()
                val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                val price = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE))
                val car = Car(carId, name, price)
                cars.add(car)
            }
        }
        cursor.close()
        return cars
    }

    fun updateCar(car: Car): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, car.name)
            put(KEY_PRICE, car.price)
        }
        val int = db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(car.id.toString()))
        Log.d("MyCar", "update - ID: ${car.id}, NAME: ${car.name}, PRICE: ${car.price}")
        val cars = getAllCars()
        for (item in cars) {
            Log.d("MyCar", "after updating - ID: ${car.id}, NAME: ${car.name}, PRICE: ${car.price}")
        }
        return int
    }

    fun deleteCar(car: Car) {
        val db = dbHelper.writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(car.id.toString()))
        Log.d("MyCar", "delete - ID: ${car.id}, NAME: ${car.name}, PRICE: ${car.price}")
        val cars = getAllCars()
        for (item in cars) {
            Log.d("MyCar", "after deleting - ID: ${car.id}, NAME: ${car.name}, PRICE: ${car.price}")
        }
        db.close()
    }

    fun getCarsCount(): Int {
        val db = dbHelper.readableDatabase
        val countQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count

    }

    fun closeDb() {
        dbHelper.close()
    }

}