package com.nirwashh.android.carsdb

import data.DatabaseManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nirwashh.android.carsdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding
    private val dbManager = DatabaseManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)




//        dbManager.addCar(Car("Toyota", "10 000$"))
//        dbManager.addCar(Car("BMW", "40 000$"))
//        dbManager.addCar(Car("KIA", "5 000$"))
//        dbManager.addCar(Car("Geely", "7 000$"))
//        dbManager.addCar(Car("Lexus", "100 000$"))


        val cars = dbManager.getAllCars()
        for (car in cars) {
            Log.d("MyCar", "contains - ID: ${car.id}, NAME: ${car.name}, PRICE: ${car.price}")
        }

        dbManager.getCar("BMW")
        val car2 = dbManager.getCar(4)
        car2.name = "Tesla"
        car2.price = "100 000$"
        dbManager.updateCar(car2)
        dbManager.deleteCar(car2)
        val count = dbManager.getCarsCount()
        Log.d("MyCar", "cars in database $count")



    }


    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
}