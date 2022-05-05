package com.nirwashh.android.carsdb

import Data.DataBaseManager
import Model.Car
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nirwashh.android.carsdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding
    private val dbManager = DataBaseManager(this)
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
            Log.d("Carrr", "ID: ${cars.indexOf(car)+1}, NAME: ${car.name}, PRICE: ${car.price}")
        }
        dbManager.getCar("BMW")
        dbManager.getCar(2)

    }


    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
}